package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomUserRepository;
import com.tilf.troke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.Calendar;

/**
 * Created by Emmanuel on 2015-09-21.
 */
@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private AuthUserContext authContext;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/logout")
    public String logout() {
        authContext.setUser(null);
        return "redirect:/";
    }

    @RequestMapping("/connexion")
    public String connexion()
    {
        return "redirect:/#openModalConnexion";
    }

    @RequestMapping(value="/auth", method= RequestMethod.POST)
    public String login(@RequestParam("iduser") String idUser, @RequestParam("pass") String pass, Model model, HttpSession session) {
        UsersEntity user = userRepository.findUsersEntityByIduserAndPass(idUser, pass);
        session.setAttribute("user", user);
        if (user == null) {
            session.setAttribute("error"," *Le nom d'utilisateur ou le mot de passe est invalide !" );
            return "redirect:/connexion";
        } else {
            authContext.setUser(user);
            return "redirect:/";
        }
    }

    @RequestMapping("/adduser")
    public String addUser(String iduser, String lastname, String firstname, String pass, String email, String telephone, String zipcode, Model model, HttpSession session) {
        BigInteger zero = BigInteger.valueOf(0);
        if (customUserRepository.checkUserExistance(iduser) == zero) {
            java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            UsersEntity user = new UsersEntity();
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
            return "home";

        } else {
            model.addAttribute("loginError", "Nom d'usager non disponible!");
            return "inscription";
        }
    }
}
