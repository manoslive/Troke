package com.tilf.troke.controller;

import com.tilf.troke.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

/**
 * Created by Manu on 2015-10-15.
 */
@Controller
public class SearchController {
    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private HomeController homeController;

/*    @RequestMapping(value = "/searchDB", method = RequestMethod.GET)
    public String searchDB(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("searchObjectList", customUserRepository.getObjectListByKeyword(keyword));
        model.addAttribute("currentpage", "search");
        model.addAttribute("adrSearch", "/searchDB?keyword=");
        model.addAttribute("leftMenu", homeController.fillLeftCatMenu());
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("searchObjectList", customUserRepository.getObjectListByKeyword(keyword));
            context.setVariable("currentpage", "search");
            context.setVariable("adrSearch", "/searchDB?keyword=");
        }
        return "redirect:/home";
    }*/
}
