package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.ObjectsEntity;
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
    public String ListCategoryItems(@RequestParam("categoryName") String categoryName, @RequestParam(value = "catIsChecked") Boolean catIsChecked, Model model, HttpSession session) {

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
            model.addAttribute("objectList", customObjectRepository.getObjectsBySubCategory(catSubCatService.getSubCatFromSet(catSubCats)));
        } else {
            model.addAttribute("objectList", null);
            model.addAttribute("objectListEmpty", true);
        }

        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", "");
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("objectListEmpty", true);
        }
        return "fragments/home/search";
    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String ListSubCategoryItems(@RequestParam("subCategoryName") String subCategoryName, @RequestParam(value = "subCatIsChecked") Boolean subCatIsChecked, HttpSession session, Model model) {
        // Modification de la liste de Cat/SubCat
        if (subCatIsChecked == true) {
            searchFilter.put(subCategoryName, subCatIsChecked);
        } else {
            searchFilter.remove(subCategoryName);
        }

        // On obtient la liste de toutes les cat/subcat qui sont cochées
        Set<String> catSubCats = getKeysByValue(searchFilter.getFilters(), true);
        if (!catSubCats.isEmpty() && !catSubCatService.getSubCatFromSet(catSubCats).isEmpty()) {
            if (!catSubCatService.getCatFromSet(catSubCats).isEmpty()) {
                model.addAttribute("objectList", customObjectRepository.getObjectsBySubCategory(catSubCatService.getSubCatFromSet(catSubCats)));
            } else {
                model.addAttribute("objectList", "");
            }
        } else {
            model.addAttribute("objectList", null);
            model.addAttribute("objectListEmpty", true);
        }

        model.addAttribute("leftMenu", fillLeftCatMenu());
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");

        // TODO THYMELEAF HACK
        if (false) {
            ObjectsEntity test = new ObjectsEntity();
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", test);
            context.setVariable("obj", test);
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade", "/startTrade?itemID=");

        }
        return "fragments/home/search";
    }

    // Test de catégorie
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String getItemByIdObject(@RequestParam("idObject") int idobject, Model model) {
        searchFilter.removeAll();
        model.addAttribute("singleobject", customObjectRepository.getObjectEntityByIdObject(idobject));
        model.addAttribute("adrItem", "/item?idObject=");
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("singleobject", customObjectRepository.getObjectEntityByIdObject(idobject));
            context.setVariable("adrItem", "/item?objectName=");
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade", "/startTrade?itemID=");
        }

        return "fragments/home/search";
    }

    @RequestMapping(value = "/searchDB", method = RequestMethod.GET)
    public String searchDB(@RequestParam("keyword") String keyword, Model model) {
        searchFilter.removeAll();
        model.addAttribute("searchObjectList", customObjectRepository.getObjectListByKeyword(keyword));
        model.addAttribute("adrSearch", "/searchDB?keyword=");
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        model.addAttribute("leftMenu", fillLeftCatMenu());
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        // On vide les checkbox
        searchFilter.getFilters().clear();

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("searchObjectList", customObjectRepository.getObjectListByKeyword(keyword));
            context.setVariable("adrSearch", "/searchDB?keyword=");
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade", "/startTrade?itemID=");
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
}
