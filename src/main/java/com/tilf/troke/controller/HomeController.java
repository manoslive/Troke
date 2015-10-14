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
    public String home(Model model, HttpSession session) {
        FillCategoryMenu(model);
        FillCategoryList(model);
        GetRecentItems(model);
        model.addAttribute("currentpage", "home");
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
        return "redirect:#openModalInscription";
    }

    @RequestMapping(value = "/inscription", method = RequestMethod.GET)
    public String inscription(UsersEntity user) {
        return "redirect:#openModalInscription";
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

        /*
        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            html += "<div><a href=\"category?categoryName=" + currentCat + "\">" + currentCat +
                    "</a><ul> ";

            List<String> subcats = customUserRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                html += "<li type='disc'><a href=\"subcategory?subCategoryName=" + currentSubCat + "&categoryName=" + currentCat + "\">" + currentSubCat + "</a></li>";
            }
            html += "</ul></div>";
        }
        */

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
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("recentobjects", objects);
        }
        return "redirect:/";
    }

    // Test de catégorie
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String ListCategoryItems(@RequestParam("categoryName") String categoryName, Model model) {

        model.addAttribute("objectList", customUserRepository.getObjectsByCategory(categoryName));
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", customUserRepository.getObjectsByCategory(categoryName));
        }

        model.addAttribute("objectList", customUserRepository.getObjectsByCategory(categoryName));
        model.addAttribute("currentpage", "search");
        return "forward:/";
    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String ListSubCategoryItems(@RequestParam("subCategoryName") String subCategoryName, Model model) {

        model.addAttribute("objectList", customUserRepository.getObjectsBySubCategory(subCategoryName));
        model.addAttribute("currentpage", "search");
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", customUserRepository.getObjectsBySubCategory(subCategoryName));
        }
        return "forward:/";
    }
}

