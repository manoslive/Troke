package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.CustomSearchObjectEntity;
import com.tilf.troke.filter.SearchFilter;
import com.tilf.troke.repository.CustomObjectRepository;
import com.tilf.troke.service.CatSubCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Manu on 2015-10-19.
 */

@Controller
public class SearchController {
    @Autowired
    private AuthUserContext authUserContext;

    @Autowired
    private CustomObjectRepository customObjectRepository;

    @Autowired
    private SearchFilter searchFilter;

    @Autowired
    private CatSubCatService catSubCatService;

    public static <String, Boolean> Set<String> getKeysByValue(Map<String, Boolean> map, Boolean value) {
        Set<String> keys = new HashSet<String>();
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    // Test de catégorie
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String ListCategoryItems(@RequestParam("categoryName") String categoryName, @RequestParam(value = "catIsChecked") Boolean catIsChecked, @RequestParam(value = "page", required = false) String page, @RequestParam(value = "number", required = false) Integer number, Model model, HttpSession session) {
        List<CustomSearchObjectEntity> list = new ArrayList<>();
        Integer totalPageNumber = null;
        // Modification de la liste de Cat/SubCat
        if (catIsChecked == true) {
            searchFilter.put(categoryName, true);
            List<String> subCatList = customObjectRepository.getSubCatListByCategoryName(categoryName);
            for (int i = 0; i < subCatList.size(); i++) {
                if (!searchFilter.exists(subCatList.get(i))) {
                    searchFilter.put(subCatList.get(i), true);
                }
            }
        } else {
            searchFilter.remove(categoryName);
            List<String> subCatList = customObjectRepository.getSubCatListByCategoryName(categoryName);
            for (int i = 0; i < subCatList.size(); i++) {
                if (searchFilter.exists(subCatList.get(i))) {
                    searchFilter.remove(subCatList.get(i));
                }
            }
        }

        // On obtient la liste de toutes les cat/subcat qui sont cochées
        Set<String> catSubCats = getKeysByValue(searchFilter.getFilters(), true);
        // si la liste n'est pas nulle
        if (!catSubCats.isEmpty()) {
            // Transformer la liste en liste de custom object entity
            list = customObjectRepository.getObjectsBySubCategory(catSubCatService.getSubCatFromSet(catSubCats));
        } else {
            list = null;
            model.addAttribute("objectListEmpty", true);
        }

        // Début

        List<List<CustomSearchObjectEntity>> pageList = new ArrayList<>();
        Integer pageNumber = null;

        // Si number est nul, on lui met 10 par défaut
        if (number == null) {
            number = 10;
        }
        // Nombre total de pages de résultats
        if (list != null) {
            Double numberDouble = Double.parseDouble(number.toString());
            totalPageNumber = (int) Math.ceil((list.size() / numberDouble));

            // On vérifie si suivant ou précédent est reçu
            // Notons que la première page est à 0
            if (page != null) {
                if (!isInteger(page)) {
                    if (page.contains("plus")) {// Suivant
                        pageNumber = Integer.parseInt(page.replace("plus", "").trim()); // On enlève le char qui indentifiait la direction
                        if (pageNumber < totalPageNumber) {
                            pageNumber++;
                        } else {
                            pageNumber = totalPageNumber;
                        }
                    } else if (page.contains("minus")) { // Précédent
                        pageNumber = Integer.parseInt(page.replace("minus", "").trim()); // On enlève le char qui indentifiait la direction
                        if (pageNumber > 1) {
                            pageNumber--;
                        } else {
                            pageNumber = 1; // la première page dans le tableau
                        }
                    }
                } else {
                    pageNumber = Integer.parseInt(page);
                    if (pageNumber < 0) {
                        pageNumber = 1;
                    } else if (pageNumber > totalPageNumber) {
                        pageNumber = totalPageNumber;
                    }
                }
            } else { // Si page est nulle, on lui affecte 1 pour la première page
                pageNumber = 1;
            }

            // Création des pages (liste de liste de customsearchobjectentity)
            if (list.size() > number) {
                for (int i = 0; i < totalPageNumber; i++) {
                    List<CustomSearchObjectEntity> internalList = new ArrayList<>();
                    if (i < totalPageNumber - 1) {
                        for (int j = 0; j < number; j++) {
                            if (i == 0) {
                                internalList.add(list.get(j));
                            } else {
                                internalList.add(list.get((i * number) + j));
                            }
                        }
                        pageList.add(internalList);
                    } else {
                        for (int j = 0; j < list.size() % number; j++) {
                            internalList.add(list.get((i * number) + j));
                        }
                        pageList.add(internalList);
                    }
                }
                model.addAttribute("objectList", pageList.get(pageNumber - 1)); // On transforme pageNumber en index
            } else {
                model.addAttribute("objectList", list);
            }
            model.addAttribute("totalobjetnumber", list);
        }

        // Attribut de modèle pour les pages
        model.addAttribute("currentpage", pageNumber); // On transforme ici l'index du tableau en page
        model.addAttribute("numberperpage", number);
        model.addAttribute("numberofpage", totalPageNumber);


        // fin

        model.addAttribute("adrGetRequest", "/category?categoryName=" + categoryName + "&amp;catIsChecked=" + catIsChecked);
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", list);
            // context.setVariable("obj", test);
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("objectListEmpty", true);
        }
        return "fragments/home/search";
    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String ListSubCategoryItems(@RequestParam("subCategoryName") String subCategoryName, @RequestParam(value = "subCatIsChecked") Boolean subCatIsChecked, @RequestParam(value = "page", required = false) String page, @RequestParam(value = "number", required = false) Integer number, HttpSession session, Model model) {
        List<CustomSearchObjectEntity> list = new ArrayList<>();
        Integer totalPageNumber = null;
        // Modification de la liste de Cat/SubCat
        if (subCatIsChecked == true) {
            searchFilter.put(subCategoryName, subCatIsChecked);
        } else {
            searchFilter.remove(subCategoryName);
        }

        // On obtient la liste de toutes les cat/subcat qui sont cochées
        Set<String> catSubCats = getKeysByValue(searchFilter.getFilters(), true);
        if (!catSubCats.isEmpty() && !catSubCatService.getSubCatFromSet(catSubCats).isEmpty()) {
            if (!catSubCatService.getSubCatFromSet(catSubCats).isEmpty()) {
                list = customObjectRepository.getObjectsBySubCategory(catSubCatService.getSubCatFromSet(catSubCats));


            } else {
                list = null;
            }
        } else {
            list = null;
            model.addAttribute("objectListEmpty", true);
        }

        // debut

        List<List<CustomSearchObjectEntity>> pageList = new ArrayList<>();
        Integer pageNumber = null;

        // Si number est nul, on lui met 10 par défaut
        if (number == null) {
            number = 10;
        }

        // Nombre total de pages de résultats
        if (list != null) {
            Double numberDouble = Double.parseDouble(number.toString());
            totalPageNumber = (int) Math.ceil((list.size() / numberDouble));


            // On vérifie si suivant ou précédent est reçu
            // Notons que la première page est à 0
            if (page != null) {
                if (!isInteger(page)) {
                    if (page.contains("plus")) {// Suivant
                        pageNumber = Integer.parseInt(page.replace("plus", "").trim()); // On enlève le char qui indentifiait la direction
                        if (pageNumber < totalPageNumber) {
                            pageNumber++;
                        } else {
                            pageNumber = totalPageNumber;
                        }
                    } else if (page.contains("minus")) { // Précédent
                        pageNumber = Integer.parseInt(page.replace("minus", "").trim()); // On enlève le char qui indentifiait la direction
                        if (pageNumber > 1) {
                            pageNumber--;
                        } else {
                            pageNumber = 1; // la première page dans le tableau
                        }
                    }
                } else {
                    pageNumber = Integer.parseInt(page);
                    if (pageNumber < 0) {
                        pageNumber = 1;
                    } else if (pageNumber > totalPageNumber) {
                        pageNumber = totalPageNumber;
                    }
                }
            } else { // Si page est nulle, on lui affecte 0 pour la première page
                pageNumber = 1;
            }

            // Création des pages (liste de liste de customsearchobjectentity)
            if (list.size() > number) {
                for (int i = 0; i < totalPageNumber; i++) {
                    List<CustomSearchObjectEntity> internalList = new ArrayList<>();
                    if (i < totalPageNumber - 1) {
                        for (int j = 0; j < number; j++) {
                            if (i == 0) {
                                internalList.add(list.get(j));
                            } else {
                                internalList.add(list.get((i * number) + j));
                            }
                        }
                        pageList.add(internalList);
                    } else {
                        for (int j = 0; j < list.size() % number; j++) {
                            internalList.add(list.get((i * number) + j));
                        }
                        pageList.add(internalList);
                    }
                }
                model.addAttribute("objectList", pageList.get(pageNumber - 1)); // On transforme pageNumber en index
            } else {
                model.addAttribute("objectList", list);
            }
            model.addAttribute("totalobjetnumber", list);
        }

        // Attribut de modèle pour les pages
        model.addAttribute("currentpage", pageNumber); // On transforme ici l'index du tableau en page
        model.addAttribute("numberperpage", number);
        model.addAttribute("numberofpage", totalPageNumber);


        // fin
        model.addAttribute("adrGetRequest", "/subcategory?subCategoryName=" + subCategoryName + "&amp;subCatIsChecked=" + subCatIsChecked);
        model.addAttribute("leftMenu", fillLeftCatMenu());
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", list);
            //context.setVariable("obj", test);
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade", "/startTrade?itemID=");

        }
        return "fragments/home/search";
    }

    // Test de catégorie
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String getItemByIdObject(@RequestParam("idObject") int idobject, Model model) {
        // Vide la liste concaténée d'objets
        searchFilter.removeAll();
        CustomSearchObjectEntity objet = customObjectRepository.getCustomsearchobjectentityByIdObject(idobject);
        model.addAttribute("singleobject", objet);
        model.addAttribute("adrItem", "/item?idObject=");
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("singleobject", objet);
            context.setVariable("adrItem", "/item?objectName=");
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade", "/startTrade?itemID=");
        }

        return "fragments/home/search";
    }

    @RequestMapping(value = "/searchDB", method = RequestMethod.GET)
    public String searchDB(@RequestParam("keyword") String keyword, @RequestParam(value = "page", required = false) String page, @RequestParam(value = "number", required = false) Integer number, Model model, HttpSession session) {
        searchFilter.removeAll();
        List<CustomSearchObjectEntity> list = customObjectRepository.getObjectListByKeyword(keyword);
        List<List<CustomSearchObjectEntity>> pageList = new ArrayList<>();
        Integer pageNumber = null;

        // Si number est nul, on lui met 10 par défaut
        if (number == null) {
            number = 10;
        }

        // Nombre total de pages de résultats
        Double numberDouble = Double.parseDouble(number.toString());
        int totalPageNumber = (int) Math.ceil((list.size() / numberDouble));

        // On vérifie si suivant ou précédent est reçu
        // Notons que la première page est à 0
        if (page != null) {
            if (!isInteger(page)) {
                if (page.contains("plus")) {// Suivant
                    pageNumber = Integer.parseInt(page.replace("plus", "").trim()); // On enlève le char qui indentifiait la direction
                    if (pageNumber < totalPageNumber) {
                        pageNumber++;
                    } else {
                        pageNumber = totalPageNumber;
                    }
                } else if (page.contains("minus")) { // Précédent
                    pageNumber = Integer.parseInt(page.replace("minus", "").trim()); // On enlève le char qui indentifiait la direction
                    if (pageNumber > 1) {
                        pageNumber--;
                    } else {
                        pageNumber = 1; // la première page dans le tableau
                    }
                }
            } else {
                pageNumber = Integer.parseInt(page);
                if (pageNumber < 0) {
                    pageNumber = 1;
                } else if (pageNumber > totalPageNumber) {
                    pageNumber = totalPageNumber;
                }
            }
        } else { // Si page est nulle, on lui affecte 0 pour la première page
            pageNumber = 1;
        }

        // Création des pages (liste de liste de customsearchobjectentity)
        if (list.size() > number) {
            for (int i = 0; i < totalPageNumber; i++) {
                List<CustomSearchObjectEntity> internalList = new ArrayList<>();
                if (i < totalPageNumber - 1) {
                    for (int j = 0; j < number; j++) {
                        if (i == 0) {
                            internalList.add(list.get(j));
                        } else {
                            internalList.add(list.get((i * number) + j));
                        }
                    }
                    pageList.add(internalList);
                } else {
                    for (int j = 0; j < list.size() % number; j++) {
                        internalList.add(list.get((i * number) + j));
                    }
                    pageList.add(internalList);
                }
            }
            model.addAttribute("searchObjectList", pageList.get(pageNumber - 1)); // On transforme pageNumber en index
        } else {
            model.addAttribute("searchObjectList", list);
        }

        // Attribut de modèle pour les pages
        model.addAttribute("currentpage", pageNumber); // On transforme ici l'index du tableau en page
        model.addAttribute("numberperpage", number);
        model.addAttribute("numberofpage", totalPageNumber);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalobjetnumber", list);

        // Autres attributs de modèle
        model.addAttribute("adrSearch", "/searchDB?keyword=");
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        model.addAttribute("leftMenu", fillLeftCatMenu());
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");

        // On vide les checkbox
        searchFilter.getFilters().clear();

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("searchObjectList", list);
            context.setVariable("adrSearch", "/searchDB?keyword=");
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade", "/startTrade?itemID=");
            context.setVariable("currentpage", page);
            context.setVariable("numberperpage", number);
            context.setVariable("numberofpage", list.size() / number);
            context.setVariable("keyword", keyword);
        }
        return "fragments/home/search";
    }

    public String fillLeftCatMenu() {
        String html = new String();
        List<String> cats = customObjectRepository.getAllCategories();
        String isSubCatChecked = "";
        String isCatChecked = "";

        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            isCatChecked = searchFilter.get(currentCat) == Boolean.TRUE ? "checked" : "";
            html += "<button type=\"button\" class=\"btn btn-info btnBootPerso\" data-toggle=\"collapse\" data-target=\"#" + currentCat.toLowerCase() + "Category\" onclick=\"checkCB('" + currentCat.toLowerCase() + "')\">\n" +
                    "<input onchange=\"getCheckBoxCatValue('" + currentCat + "');\" id=\"" + currentCat + "\" class=\"mainCategory catCb\" style=\"float:left;\" type=\"checkbox\"" + isCatChecked + ">" + currentCat + "</input>\n" +
                    "<select style=\"float:right;\" class=\"disappear\" disabled=\"disabled\"></select>\n" +
                    "</button>\n" +
                    "<div id=\"" + currentCat.toLowerCase() + "Category\" class=\"category-Selection collapse\">\n" +
                    "<ul>\n";

            List<String> subcats = customObjectRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                isSubCatChecked = searchFilter.get(currentSubCat) == Boolean.TRUE ? "checked" : "";
                html += "<li>" +
                        "<input onchange=\"getCheckBoxSubCatValue('" + currentSubCat + "');\" id=\"" + currentSubCat + "\" class=\"mainCategory" + currentSubCat + "subCatCb\" type=\"checkbox\"" + isSubCatChecked + ">" +
                        "<a onclick=\"getCheckBoxSubCatValueLink('" + currentSubCat + "');\">" + currentSubCat + "</a>" +
                        "</input>" +
                        "</li>\n";
            }
            html += "</ul></div>\n";
        }
        return html;
    }

    // Méthode qui vérifie si le String est un Integer possible
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
