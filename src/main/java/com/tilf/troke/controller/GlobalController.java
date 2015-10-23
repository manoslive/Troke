package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.domain.SearchFilter;
import com.tilf.troke.domain.UserSignupForm;
import com.tilf.troke.entity.UsersEntity;
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

    @Autowired
    private SearchFilter searchFilter;

    @ModelAttribute
    public void authAttributes(Model model)
    {
        model.addAttribute("authUser", authUserContext.getUser());
        model.addAttribute("searchFilter", searchFilter.getFilters());
        model.addAttribute("userSignupForm", new UserSignupForm());
    }

}
