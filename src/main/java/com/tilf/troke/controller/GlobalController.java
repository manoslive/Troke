package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Emmanuel on 2015-09-21.
 */
@ControllerAdvice
public class GlobalController {
    @Autowired
    private AuthUserContext authUserContext;

    @ModelAttribute
    public void authAttributes(Model model)
    {
        model.addAttribute("authUser", authUserContext.getUser());
    }
}
