package com.tilf.troke.controller;

import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.expression.Objects;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @RequestMapping("/inscription")
    public String inscription(Model model) {
        return "inscription";
    }

    public void FillCategoryMenu(Model model) {
        List<String> categoryList = customUserRepository.getAllCategories();

        model.addAttribute("categoryList", categoryList);
    }

    public String FillCategoryList(Model model) {
        String html = "";
        List<String> cats = customUserRepository.getAllCategories();

        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            html += "<div><a href=\"/category?categoryName=" + currentCat + "\">" + currentCat +
                    "</a><ul> ";

            List<String> subcats = customUserRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                html += "<li type='disc'><a href=\"/subcategory?subCategoryName=" + currentSubCat + "&categoryName=" + currentCat + "\">" + currentSubCat + "</a></li>";
            }
            html += "</ul></div>";
        }
        model.addAttribute("allCategories", html);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("allCategories", html);
            // context.setVariable("smlStrany", smlStranaRepository.findBySmlouva(smlouva));
        }
        return "redirect:/";
    }

    public String GetTenNewestItems(Model model) {
        String html = "";
        List<String> objects = customUserRepository.getLast10Objects();

        for (Iterator<String> i = objects.iterator(); i.hasNext(); ) {
            html += "<div>" + i.next() + "</div>";
        }
        model.addAttribute("last10objects", html);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("last10objects", html);
            // context.setVariable("smlStrany", smlStranaRepository.findBySmlouva(smlouva));
        }
        return "redirect:/";
    }

    // Test de catégorie
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String ListCategoryItems(@RequestParam("categoryName") String categoryName, Model model) {
        String url = "";
        /*
        List<ObjectsEntity> objects = customUserRepository.getObjectsByCategory(categoryName);

        for (ObjectsEntity obj : objects) {
            url += "<div>" + obj.getNameObject() + "</div>";
        }
        */
        model.addAttribute("recherche", "<div>" + categoryName + "</div>");
        //model.addAttribute("recherche", url);

        return "forward:/";
    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String ListSubCategoryItems(@RequestParam("categoryName") String categoryName, @RequestParam("subCategoryName") String subCategoryName, Model model) {
        String url = "";
        // List<ObjectsEntity> objects = customUserRepository.getObjectsBySubCategory(subCategoryName);

        /*
        for (Object[] obj : test) {
            ObjectsEntity objects = test[0];
            url += "<div>" + obj.getNameObject() + "</div>";
        }


        for (Iterator<Object[]> i = test.iterator(); i.hasNext();) {
            ObjectsEntity oe = (ObjectsEntity)i.next();
        }

        for(Object u: objects) {
            ObjectsEntity user = (ObjectsEntity) u;
            url += "<div>" + user.getNameObject() + "</div>";
        }
        */
        // model.addAttribute("recherche", "<div>" + subCategoryName + "</div>");
        // model.addAttribute("recherche", url);
        model.addAttribute("objectList", customUserRepository.getObjectsBySubCategory(subCategoryName));
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("objectList", customUserRepository.getObjectsBySubCategory(subCategoryName));
            // context.setVariable("smlStrany", smlStranaRepository.findBySmlouva(smlouva));
        }
        return "forward:/";
    }
}

