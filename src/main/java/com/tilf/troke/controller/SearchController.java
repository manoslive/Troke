package com.tilf.troke.controller;

import com.tilf.troke.filter.SearchFilter;
import com.tilf.troke.repository.CustomObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Manu on 2015-10-19.
 */

@Controller
public class SearchController {
    @Autowired
    private CustomObjectRepository customObjectRepository;

    @Autowired
    private SearchFilter searchFilter;

    // Test de catégorie
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String ListCategoryItems(@RequestParam("categoryName") String categoryName, @RequestParam(value = "catIsChecked", required = false) Boolean catIsChecked, Model model, HttpSession session) {
        model.addAttribute("objectList", customObjectRepository.getObjectsByCategory(categoryName));
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        if (catIsChecked.equals(null))
            searchFilter.put(categoryName, true);
        else
            searchFilter.put(categoryName, catIsChecked);
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", customObjectRepository.getObjectsByCategory(categoryName));
            context.setVariable("leftMenu", fillLeftCatMenu());
        }
        model.addAttribute("objectList", customObjectRepository.getObjectsByCategory(categoryName));
        return "fragments/home/search";
    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String ListSubCategoryItems(@RequestParam("subCategoryName") String subCategoryName, @RequestParam(value = "subCatIsChecked", required = false) Boolean subCatIsChecked, HttpSession session, Model model) {
        model.addAttribute("objectList", customObjectRepository.getObjectsBySubCategory(subCategoryName));
        searchFilter.put(subCategoryName, subCatIsChecked);
        model.addAttribute("leftMenu", fillLeftCatMenu());
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", customObjectRepository.getObjectsBySubCategory(subCategoryName));
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade","/startTrade?itemID=" );
        }
        return "fragments/home/search";
    }

    // Test de catégorie
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String getItemByIdObject(@RequestParam("idObject") int idobject, Model model) {
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
            context.setVariable("adrStartTrade","/startTrade?itemID=" );
        }
        return "fragments/home/search";
    }

    public String fillLeftCatMenu() {
        String html = new String();
        List<String> cats = customObjectRepository.getAllCategories();
        String isSubCatChecked = "";
        String isCatChecked = "";

/*        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            isCatChecked = searchFilter.get(currentCat) == Boolean.TRUE ? "checked" : "";
            html += "<button type=\"button\" class=\"btn btn-info btnBootPerso\" data-toggle=\"collapse\" data-target=\"#" + currentCat.toLowerCase() + "Category\" onclick=\"checkCB('" + currentCat.toLowerCase() + "')\">\n" +
                    "<input onchange=\"getCheckBoxCatValue('" + currentCat.toLowerCase() + "');\" id=\"" + currentCat.toLowerCase() + "\" class=\"mainCategory catCb\" style=\"float:left;\" type=\"checkbox\"><a href=\"/category?categoryName=" + currentCat + "&catIsChecked=" + isCatChecked + "\"" + isCatChecked + "></a>" + currentCat + "</input>\n" +
                    "<select style=\"float:right;\" class=\"disappear\" disabled=\"disabled\"></select>\n" +
                    "</button>\n" +
                    "<div id=\"" + currentCat.toLowerCase() + "Category\" class=\"category-Selection collapse\">\n" +
                    "<ul>\n";

            List<String> subcats = customObjectRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                isSubCatChecked = searchFilter.get(currentSubCat) == Boolean.TRUE ? "checked" : "";
                html += "<li>" +
                        "<input onchange=\"getCheckBoxSubCatValue('" + currentSubCat.toLowerCase() + "');\" id=\"" + currentSubCat + "\" class=\"mainCategory" + currentSubCat.toLowerCase() + "subCatCb\" type=\"checkbox\"" + isSubCatChecked + ">" +
                        "<a href=\"/subcategory?subCategoryName=" + currentSubCat + "&subCatIsChecked=" + isSubCatChecked + "\">" + currentSubCat + "</a>" +
                        "</input>" +
                        "</li>\n";
            }
            html += "</ul></div>\n";*/

        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            isCatChecked = searchFilter.get(currentCat) == Boolean.TRUE ? "checked" : "";
            html += "<button type=\"button\" class=\"btn btn-info btnBootPerso\" data-toggle=\"collapse\" data-target=\"#" + currentCat.toLowerCase() + "Category\" onclick=\"checkCB('" + currentCat.toLowerCase() + "')\">\n" +
                    "<input onchange=\"getCheckBoxCatValue('" + currentCat.toLowerCase() + "');\" id=\"" + currentCat.toLowerCase() + "\" class=\"mainCategory catCb\" style=\"float:left;\" type=\"checkbox\"><a onclick=\"getCheckBoxSubCatValue('\" + currentSubCat.toLowerCase() + \"');\"" + isCatChecked + "></a>" + currentCat + "</input>\n" +
                    "<select style=\"float:right;\" class=\"disappear\" disabled=\"disabled\"></select>\n" +
                    "</button>\n" +
                    "<div id=\"" + currentCat.toLowerCase() + "Category\" class=\"category-Selection collapse\">\n" +
                    "<ul>\n";

            List<String> subcats = customObjectRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                isSubCatChecked = searchFilter.get(currentSubCat) == Boolean.TRUE ? "checked" : "";
                html += "<li>" +
                        "<input onchange=\"getCheckBoxSubCatValue('" + currentSubCat.toLowerCase() + "');\" id=\"" + currentSubCat + "\" class=\"mainCategory" + currentSubCat.toLowerCase() + "subCatCb\" type=\"checkbox\"" + isSubCatChecked + ">" +
                        "<a onclick=\"getCheckBoxSubCatValue('" + currentSubCat.toLowerCase() + "');\">" + currentSubCat + "</a>" +
                        "</input>" +
                        "</li>\n";
            }
            html += "</ul></div>\n";
        }
        return html;
    }
}
