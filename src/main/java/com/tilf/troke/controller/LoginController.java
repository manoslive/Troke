package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpSession;

/**
 * Created by Emmanuel on 2015-09-21.
 */
@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUserContext authContext;

    @RequestMapping("/login")
    public String login() {
        return "profilUser";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/logout")
    public String logout() {
        authContext.setUser(null);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("authUser", authContext.getUser());
        }
        return "redirect:/";
    }

    @RequestMapping("/connexion")
    public String connexion(HttpSession session)
    {
        return "redirect:#openModalConnexion";
    }

    @RequestMapping(value="/connexionNew", method = RequestMethod.GET)
    public String connexionNew()
    {
        return "/#openModalConnexion";
    }

    @RequestMapping("/openModalConnexion")
    public String openModalConnexion(HttpSession session)
    {
        session.removeAttribute("error");
        return "redirect:#openModalConnexion"; // FIXME la page refresh au moment du click
    }

    @RequestMapping(value="/auth", method= RequestMethod.POST)
    public String login(@RequestParam("iduser") String idUser, @RequestParam("pass") String pass, Model model, HttpSession session) {
        UsersEntity user = userRepository.findUsersEntityByIduserAndPass(idUser, pass);
        session.setAttribute("user", user);
        if(idUser.isEmpty())
        {
            session.setAttribute("error", "* Veuillez entrer un nom d'utilisateur");
            // TODO THYMELEAF HACK
            if (false) {
                WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                context.setVariable("user", user);
                context.setVariable("error", "* Veuillez entrer un nom d'utilisateur");
            }
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
