package com.tilf.troke.controller;

import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomUserRepository;
import com.tilf.troke.repository.UserRepository;
import com.tilf.troke.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
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

    @Autowired
    private UserValidator userValidator;

    /*
    @RequestMapping("/get")
    public String getUser(@RequestParam("iduser") String idUser, Model model) {
        UsersEntity user = customUserRepository.findUserById(idUser);
        model.addAttribute("user", user);
        return "user";
    }
    @RequestMapping("/listusers")
    public String getAllUsers(Model model) {
        List<UsersEntity> usersList = customUserRepository.getAllUsers();
        model.addAttribute("userList", usersList);
        return "site/listusers";
    }
    */
    @RequestMapping(value = "/adduser",  params={"user"} ,method = RequestMethod.POST)
    public String adduser(@ModelAttribute("user") @Valid UsersEntity user, BindingResult result) {
            userValidator.validate(user, result);

            java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            /*
            if (result.hasErrors()) {
                model.addAttribute("errors", result.getFieldErrors());
                // TODO THYMELEAF HACK
                if (false) {
                    WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                    context.setVariable("errors", result.getFieldErrors());
                }
                return "redirect:/inscription";
            } else {
            */
            if(!result.hasErrors()){
                user.setAvatar(null);
                user.setIsbanned("N");
                user.setIsonline("N");
                user.setCreationdate(now);
                user.setPermissionlevel(0);
                user.setIsvip("N");
                userRepository.save(user);
                return "redirect:/";
            }
        return "redirect:/inscription";
    }



    /*
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "iduser", required = true) String iduser, @RequestParam(value = "lastname", required = true) String lastname, @RequestParam(value = "firstname", required = true) String firstname,
                          @RequestParam(value = "pass", required = true) String pass, @RequestParam(value = "pass_confirm", required = true) String pass_confirm, @RequestParam(value = "email", required = true) String email,
                          @RequestParam(value = "email_confirm", required = true) String email_confirm, @RequestParam(value = "telephone", required = true) String telephone, @RequestParam(value = "zipcode", required = true) String zipcode, Model model, HttpSession session) {
        BigInteger zero = BigInteger.valueOf(0);
        BigInteger userExistance = customUserRepository.checkUserExistance(iduser);
        if (!email.equals(email_confirm)) {
            session.setAttribute("errorInscription", "Les 2 champ de courriel doivent être identiques");
            return "forward:/inscription";
        } else if (!pass.equals(pass_confirm)) {
            session.setAttribute("errorInscription", "Les 2 champ de de mots de passes doivent être identiques");
            return "forward:/inscription";
        } else if (userExistance == zero) {
            UsersEntity user = new UsersEntity();
            java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            user.setIduser(iduser);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setPass(pass);
            user.setAvatar(null);
            user.setIsbanned("N");
            user.setIsonline("N");
            user.setCreationdate(now);
            user.setEmail(email);
            user.setTelephone(telephone);
            user.setZipcode(zipcode);
            user.setPermissionlevel(0);
            user.setIsvip("N");

            userRepository.save(user);

            return "redirect:/";
        } else {
            session.setAttribute("errorInscription", "Nom d'usager non disponible!");
            return "forward:/inscription";
        }
    }
    */
}