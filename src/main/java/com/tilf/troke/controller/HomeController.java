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
        GetTenNewestItems(model);
        return "home";
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
        model.addAttribute("allCategories", html);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("allCategories", html);
        }
    }

    public String GetTenNewestItems(Model model) {
        List<ObjectsEntity> objects = customUserRepository.getTenMostRecentObjects();
        model.addAttribute("last10objects", objects);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("last10objects", objects);
            // context.setVariable("smlStrany", smlStranaRepository.findBySmlouva(smlouva));
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

        return "forward:/";
    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String ListSubCategoryItems(@RequestParam("subCategoryName") String subCategoryName, Model model) {

        model.addAttribute("objectList", customUserRepository.getObjectsBySubCategory(subCategoryName));
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", customUserRepository.getObjectsBySubCategory(subCategoryName));
        }
        return "forward:/";
    }
}

