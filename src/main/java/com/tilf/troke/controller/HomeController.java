package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private AuthUserContext authContext;

    @Autowired
    private CustomObjectRepository customObjectRepository;

    @RequestMapping("/")
    public String root(Model model, HttpSession session) {
        FillCategoryMenu(model, session);
        FillCategoryList(model);
        GetRecentItems(model);
        return "fragments/home/home";
    }

    @RequestMapping("/home")
    public String redirectHome(Model model, HttpSession session) {
        FillCategoryMenu(model, session);
        FillCategoryList(model);
        GetRecentItems(model);
        return "fragments/home/home";
    }

    @ModelAttribute("categoryList")
    public void FillCategoryMenu(Model model, HttpSession session) {
        List<String> categoryList = customObjectRepository.getAllCategories();

        session.setAttribute("categoryList", categoryList);
        session.setAttribute("addressCatName", "category?categoryName=");
        session.setAttribute("addressChecked", "&catIsChecked=true");
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("categoryList", categoryList);
            context.setVariable("addressCatName", "category?categoryName=");
            context.setVariable("addressChecked", "&catIsChecked=true");
        }
    }

    public void FillCategoryList(Model model) {
        String html = "";
        List<String> cats = customObjectRepository.getAllCategories();
        model.addAttribute("catlist", cats);
        model.addAttribute("adrcat", "/category?categoryName=");

        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            html += "<div class=\"myCategory\"><header><a href=\"category?categoryName=" + currentCat + "&catIsChecked=true" + "\">" + currentCat + "</a></header><ul> ";

            List<String> subcats = customObjectRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                html += "<li class=\"lienCategorie\"><a href=\"subcategory?subCategoryName=" + currentSubCat + "&subCatIsChecked=true" + "\">" + currentSubCat + "</a></li>";
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
        List<ObjectsEntity> objects = customObjectRepository.getRecentItems();
        model.addAttribute("recentobjects", objects);
        model.addAttribute("adrItem", "/item?idObject=");

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("recentobjects", objects);
            context.setVariable("adrItem", "/item?idObject=");
        }
        return "fragments/home/home";
    }

    // model pour appeler la page about et changer le current page a about ..
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(Model model) {
        return "fragments/home/about";
    }

    // appel de profil
    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    public String Profil(Model model,
                         HttpSession session) {
        UsersEntity user = authContext.getUser();

        if(user != null) {

            model.addAttribute("userActif", user);
            List<ObjectsEntity> list = customObjectRepository.getListObjectByUserId(authContext.getUser().getIduser());
            model.addAttribute("userInventory", list);

            // TODO THYMELEAF HACK
            if (false) {
                WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                context.setVariable("userActif", user);
                context.setVariable("userInventory", list);

            }
            return "fragments/site/profilUser";

        }else
        {
            session.removeAttribute("error");
            return "redirect:#openModalConnexion";
        }
    }
}