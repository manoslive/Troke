package com.tilf.troke.controller;

import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomUserRepository;
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
 * Created by Emmanuel on 2015-09-20.
 */
@Controller
public class HomeController {

    @Autowired
    private CustomUserRepository customUserRepository;

    @RequestMapping("/")
    public String root(Model model) {
        FillCategoryMenu(model);
        FillCategoryList(model);
        GetRecentItems(model);
        model.addAttribute("currentpage", "home");
        return "template";
    }

    @RequestMapping("/home")
    public String redirectHome(Model model) {
        FillCategoryMenu(model);
        FillCategoryList(model);
        GetRecentItems(model);
        // model.addAttribute("currentpage", "search"); // FIXME Petit problème ici currentpage semble être nul lors d'une recherche
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("currentpage", "home");
        }
        return "template";
    }

    @RequestMapping(value = "/inscriptionNew", method = RequestMethod.GET)
    public String inscriptionNew(HttpSession session, UsersEntity user) {
        session.removeAttribute("errorInscription");
        return "#openModalInscription";
    }

    @RequestMapping(value = "/inscription", method = RequestMethod.GET)
    public String inscription(UsersEntity user) {
        return "#openModalInscription";
    }

    public void FillCategoryMenu(Model model) {
        List<String> categoryList = customUserRepository.getAllCategories();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("address", "category?categoryName=");
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("categoryList", categoryList);
            context.setVariable("address", "category?categoryName=");
        }
    }

    public void FillCategoryList(Model model) {
        String html = "";
        List<String> cats = customUserRepository.getAllCategories();
        model.addAttribute("catlist", cats);
        model.addAttribute("adrcat", "/category?categoryName=");

        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            html += "<div class=\"myCategory\"><header><a href=\"category?categoryName=" + currentCat + "\">" + currentCat +
                    "</a></header><ul> ";

            List<String> subcats = customUserRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                html += "<li class=\"lienCategorie\"><a href=\"subcategory?subCategoryName=" + currentSubCat + "&categoryName=" + currentCat + "\">" + currentSubCat + "</a></li>";
            }
            html += "</ul></div>";
        }

        model.addAttribute("catsubcatlist", html);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("catsubcatlist", html);
            context.setVariable("catlist", cats);
            context.setVariable("adrcat", "/category?categoryName=");
        }
    }

    public String GetRecentItems(Model model) {
        List<ObjectsEntity> objects = customUserRepository.getRecentItems();
        model.addAttribute("recentobjects", objects);
        model.addAttribute("adrItem", "/item?idObject=");

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("recentobjects", objects);
            context.setVariable("adrItem", "/item?idObject=");

        }
        return "forward:/home";
    }

    // Test de catégorie
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String ListCategoryItems(@RequestParam("categoryName") String categoryName, Model model) {
        model.addAttribute("currentpage", "search");
        model.addAttribute("objectList", customUserRepository.getObjectsByCategory(categoryName));
        model.addAttribute("leftMenu", fillLeftCatMenu());
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", customUserRepository.getObjectsByCategory(categoryName));
            context.setVariable("leftMenu", fillLeftCatMenu());
        }

        model.addAttribute("objectList", customUserRepository.getObjectsByCategory(categoryName));

        return "forward:/home";
    }
    // appel de profil
    @RequestMapping(value="/profil", method = RequestMethod.GET)
    public String Profil(Model model)
    {
        model.addAttribute("currentpage", "profil");
        return "forward:/home";
    }


    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String ListSubCategoryItems(@RequestParam("subCategoryName") String subCategoryName, Model model) {
        model.addAttribute("currentpage", "search");
        model.addAttribute("objectList", customUserRepository.getObjectsBySubCategory(subCategoryName));
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", customUserRepository.getObjectsBySubCategory(subCategoryName));
            context.setVariable("leftMenu", fillLeftCatMenu());
        }
        return "forward:/home";
    }
    // model pour appeler la page about et changer le current page a about ..
    @RequestMapping(value="/about", method = RequestMethod.GET)
    public String about(Model model)
    {
        model.addAttribute("currentpage", "about");
        return "forward:/home";
    }

    // Test de catégorie
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String getItemByIdObject(@RequestParam("idObject") int idobject, Model model) {
        model.addAttribute("currentpage", "search");
        model.addAttribute("singleobject", customUserRepository.getObjectEntityByIdObject(idobject));
        model.addAttribute("adrItem", "/item?idObject=");
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("singleobject", customUserRepository.getObjectEntityByIdObject(idobject));
            context.setVariable("adrItem", "/item?objectName=");
            context.setVariable("leftMenu", fillLeftCatMenu());
        }

        return "forward:/home";
    }

    @RequestMapping(value = "/searchDB", method = RequestMethod.GET)
    public String searchDB(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("searchObjectList", customUserRepository.getObjectListByKeyword(keyword));
        model.addAttribute("currentpage", "search");
        model.addAttribute("adrSearch", "/searchDB?keyword=");
        model.addAttribute("leftMenu", fillLeftCatMenu());
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("searchObjectList", customUserRepository.getObjectListByKeyword(keyword));
            context.setVariable("currentpage", "search");
            context.setVariable("adrSearch", "/searchDB?keyword=");
            context.setVariable("leftMenu", fillLeftCatMenu());
        }
        return "forward:/home";
    }

    public String fillLeftCatMenu() {
        String html = new String();
        List<String> cats = customUserRepository.getAllCategories();

        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            html += "<button type=\"button\" class=\"btn btn-info btnBootPerso\" data-toggle=\"collapse\" data-target=\"#" + currentCat.toLowerCase() + "Category\"\n" +
                    "onclick=\"checkCB('" + currentCat.toLowerCase() + "')\">\n" +
                    "<input id=\"" + currentCat.toLowerCase() +  "\" class=\"mainCategory\" style=\"float:left;\" type=\"checkbox\">" + currentCat + "</input>\n" +
                    "<select style=\"float:right;\" class=\"disappear\" disabled=\"disabled\"></select>\n" +
                    "</button>\n" +
                    "<div id=\"" + currentCat.toLowerCase() + "Category\" class=\"category-Selection collapse\">\n" +
                    "<ul>\n";

            List<String> subcats = customUserRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                html += "<li><input class=\"" + currentCat.toLowerCase() + "\" type=\"checkbox\">" + currentSubCat + "</input></li>\n";
            }
            html += "</ul></div>\n";
        }
        return html;
    }
}

