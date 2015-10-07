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
    public String connexion(HttpSession session)
    {
        return "redirect:#openModalConnexion";
    }
    @RequestMapping("/openModalConnexion")
    public String openModalConnexion(HttpSession session)
    {
        session.removeAttribute("error");
        return "redirect:/connexion";
    }

    @RequestMapping(value="/auth", method= RequestMethod.POST)
    public String login(@RequestParam("iduser") String idUser, @RequestParam("pass") String pass, Model model, HttpSession session) {
        UsersEntity user = userRepository.findUsersEntityByIduserAndPass(idUser, pass);
        session.setAttribute("user", user);
        if(idUser.isEmpty())
        {
            session.setAttribute("error", "* Veuillez entrer un nom d'utilisateur");
            return "redirect:/connexion";
        }
        else if(pass.isEmpty())
        {
            session.setAttribute("error", "* Veuillez entrer un mot de passe");
            return "redirect:/connexion";
        }
        else if (user == null) {
            session.setAttribute("error"," * Le nom d'utilisateur ou le mot de passe est invalide !" );
            return "redirect:/connexion";
        } else {
            authContext.setUser(user);
            return "redirect:/";
        }
    }
}
