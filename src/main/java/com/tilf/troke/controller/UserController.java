package com.tilf.troke.controller;

import com.tilf.troke.domain.UserSignupForm;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomObjectRepository;
import com.tilf.troke.repository.CustomUserRepository;
import com.tilf.troke.repository.UserRepository;
import com.tilf.troke.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Emmanuel on 2015-09-20.
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private CustomObjectRepository customObjectRepository;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String adduser(@ModelAttribute("userSignupForm") @Valid UserSignupForm userSignupForm, BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        userValidator.validate(userSignupForm, result);
        java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        BigInteger checkUserExistance = customUserRepository.checkUserExistance(userSignupForm.getIduser());
        BigInteger checkEmailExistance = customUserRepository.checkEmailExistance(userSignupForm.getEmail());

        if (!result.hasErrors() &&  checkUserExistance == BigInteger.ZERO && checkEmailExistance == BigInteger.ZERO) {
            UsersEntity user = new UsersEntity();
            user.setIduser(userSignupForm.getIduser());
            user.setFirstname(userSignupForm.getFirstname());
            user.setLastname(userSignupForm.getLastname());
            user.setPass(userSignupForm.getPass());
            user.setEmail(userSignupForm.getEmail());
            user.setTelephone(userSignupForm.getTelephone());
            user.setZipcode(userSignupForm.getZipcode());
            user.setAvatar(null);
            user.setState("N");
            user.setIsonline("N");
            user.setCreationdate(now);
            user.setPermissionlevel(0);
            user.setIsvip("N");
            userRepository.save(user);
            return "forward:/";
        }
        if(checkUserExistance == BigInteger.ONE) {
            ObjectError error = new ObjectError("error.iduser", "Le nom d'usager est déjà utilisé.");
            result.addError(error);
        }
        if(checkEmailExistance == BigInteger.ONE) {
            ObjectError error = new ObjectError("error.email", "Le courriel est déjà utilisé.");
            result.addError(error);
        }
        redirectAttributes.addFlashAttribute("userSignupForm", userSignupForm);
        redirectAttributes.addFlashAttribute("fields", result);
        session.setAttribute("userInformation", userSignupForm);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("fields", result);
            context.setVariable("userSignupForm", userSignupForm);
            context.setVariable("userInformation", userSignupForm);
        }
        return "redirect:/#openModalInscription";
    }

    @RequestMapping(value = "/inscription", method = RequestMethod.GET)
    public String inscription() {
        return "#openModalInscription";
    }

    @RequestMapping(value = "/inscriptionNew", method = RequestMethod.GET)
    public String inscriptionNew(HttpSession session) {
        session.removeAttribute("errorInscription");
        return "#openModalInscription";
    }
}