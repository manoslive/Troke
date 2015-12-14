package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.domain.Add;
import com.tilf.troke.entity.*;
import com.tilf.troke.filter.SearchFilter;
import com.tilf.troke.repository.*;
import com.tilf.troke.service.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

    @Autowired
    private CustomImageObjectRepository customImageObjectRepository;

    @Autowired
    private CustomSubcategoryRepository customSubcategoryRepository;

    @Autowired
    private CustomCategoryRepository customCategoryRepository;

    @Autowired
    private SearchFilter searchFilter;

    @Autowired
    private CustomMyTradeRepository customMyTradeRepository;

    @Autowired
    private SmtpMailSender smtpMailSender;

    @RequestMapping("/")
    public String root(Model model, HttpSession session) throws MessagingException {
        FillCategoryMenu(model, session);
        FillCategoryList(model);
        GetRecentItems(model);
        searchFilter.removeAll();
        long itemcount = customObjectRepository.getNumberOfItem();

        session.setAttribute("ItemCount", itemcount);
        List<TransactionsEntity> CountPending = new ArrayList<TransactionsEntity>();
        if(authContext.getUser() != null)
        {
           CountPending = customMyTradeRepository.getPendingTransactionsByUserID(authContext.getUser().getIduser());
            session.setAttribute("notifications", CountPending.size());
        }

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

//        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
//            String currentCat = i.next();
//            html += "<div class=\"myCategory\"><header><a href=\"category?categoryName=" + currentCat + "&catIsChecked=true" + "\">" + currentCat + "</a></header><ul> ";
//
//            List<String> subcats = customObjectRepository.getAllSubCategories(currentCat);
//            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
//                String currentSubCat = j.next();
//                html += "<li class=\"lienCategorie\"><a href=\"subcategory?subCategoryName=" + currentSubCat + "&subCatIsChecked=true" + "\">" + currentSubCat + "</a></li>";
//            }
//            html += "</ul></div>";
//        }

        html += "<div class=\"bubble\">\n" +
                "\t\t<!--<img src=\"images/LogoWorld_Transparence2.png\" class=\"Logobanner\" id=\"Logo\">-->\n" +
                "\t<div class=\"rectangle\">\n" +
                "\t\t\n" +
                "\t\t<h2 style=\"margin-top:10px;font-family:'Amaranth';font-size:20px; letter-spacing:.2em;\">CATÉGORIES</h2></div>\n" +
                "\t<div class=\"triangle-l\"></div>\n" +
                "\t<div class=\"triangle-r\"></div>\n" +
                "\t<div class=\"info\" style=\"inline-flex\">\n" +
                "\t<div>";

        for (Iterator<String> i = cats.iterator(); i.hasNext(); ) {
            String currentCat = i.next();
            html += "<ul class=\"categoriess\">\n" +
                    "\t\t<h2 style=\"float:left; text-transform:uppercase\"><a href=\"category?categoryName=" + currentCat + "&catIsChecked=true\"" + "style=\"text-decoration:none;color:white\"+ \">" + currentCat + "</a></h2> ";

            List<String> subcats = customObjectRepository.getAllSubCategories(currentCat);
            for (Iterator<String> j = subcats.iterator(); j.hasNext(); ) {
                String currentSubCat = j.next();
                html += "<li><a href=\"subcategory?subCategoryName=" + currentSubCat + "&subCatIsChecked=true\"" + "style=\"text-decoration:none;color:white\"+ \">" + currentSubCat + "</a></li>";
            }
            html += "</ul>";
        }


        html += "</div></div></div>";

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
        List<CustomSearchObjectEntity> objects = customObjectRepository.getRecentItems();
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


    @RequestMapping(value = "/profilinv", method = RequestMethod.GET)
    public String Inventaire(Model model,
                         HttpSession session,Add add){
            UsersEntity user = authContext.getUser();

            if (user != null) {
                // on ajoute a la page le user qui est loggé pour avoir ses informations
                model.addAttribute("userActif", user);

                // on va chercher la liste de tous les items du user et ensuite on l'ajoute a la page..
                List<ObjectsEntity> list = customObjectRepository.getListObjectByUserId(authContext.getUser().getIduser());
                model.addAttribute("userInventory", list);

                // pour cause d'avoir des modal vide ..
                model.addAttribute("idObjectDelete", null);

                // entity a envoyer a la page pour peupler le combobox
                List<CustomCategorySubCategoryEntity> itemCombo = new ArrayList<CustomCategorySubCategoryEntity>();


                // avoir la liste de category pour le comboBox
                List<CategoryEntity> listCat = customCategoryRepository.getAllCategory(); // liste de tout les category
                List<SubcategoryEntity> listSubCatInterne;

                for (int i = 0; i < listCat.size(); i++) {
                    // item interne de la boucle pour peupler les itemCombos.
                    CustomCategorySubCategoryEntity customInterne = new CustomCategorySubCategoryEntity();
                    listSubCatInterne = customSubcategoryRepository.getAllSubCat(listCat.get(i).getIdcategory());
                    customInterne.setCategory(listCat.get(i));
                    customInterne.setListSubCat(listSubCatInterne);
                    itemCombo.add(customInterne);

                }

                // liste pour peupler le comboBox
                model.addAttribute("itemCombo", itemCombo);

                // Liste des images pour chaque objet ...
                List<List<ImageobjectEntity>> listImage = new ArrayList<List<ImageobjectEntity>>();
                List<ImageobjectEntity> listInterne;

                for (int i = 0; i < list.size(); i++) {
                    listInterne = customImageObjectRepository.getImageObjectbyObjectId(list.get(i).getIdobject());
                    listImage.add(listInterne);

                }

                model.addAttribute("listeImage", listImage);
                // TODO THYMELEAF HACK
                if (false) {
                    WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                    context.setVariable("userActif", user);
                    context.setVariable("userInventory", list);
                    context.setVariable("listeImage", listImage);
                    context.setVariable("itemCombo", itemCombo);

                }
                return "fragments/profil/pageinventaire";

            } else {
                session.removeAttribute("error");
                return "redirect:#openModalConnexion";
            }
    }
}