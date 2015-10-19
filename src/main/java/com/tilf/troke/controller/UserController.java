package com.tilf.troke.controller;

import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomUserRepository;
import com.tilf.troke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.WebContext;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.io.Console;
import java.math.BigInteger;
import java.util.Calendar;

/**
 * Created by Emmanuel on 2015-09-20.
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String adduser(@ModelAttribute("user") @Valid UsersEntity user, BindingResult result, RedirectAttributes redirectAttributes) {

        java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        if (!result.hasErrors()) {
            user.setAvatar(null);
            user.setIsbanned("N");
            user.setIsonline("N");
            user.setCreationdate(now);
            user.setPermissionlevel(0);
            user.setIsvip("N");
            userRepository.save(user);
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("user", user);
        redirectAttributes.addFlashAttribute("fields", result);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("fields", result);
            context.setVariable("user", user);

        }

        return "redirect:/#openModalInscription";
    }
}