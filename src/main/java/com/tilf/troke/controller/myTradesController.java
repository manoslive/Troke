package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

/**
 * Created by Shaun Cooper on 2015-10-26.
 */
@Controller
public class myTradesController {
    @Autowired
    private AuthUserContext authUserContext;

    @RequestMapping(value = "/myTrades", method = RequestMethod.GET)
    public String getMyTrades(Model model) {
        UsersEntity currentUser = authUserContext.getUser();
        model.addAttribute("userActif", currentUser);

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("userActif", currentUser);
        }
        return "fragments/home/myTrades";
    }

}
