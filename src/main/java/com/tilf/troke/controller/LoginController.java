package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomUserRepository;
import com.tilf.troke.repository.UserRepository;
import com.tilf.troke.service.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

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

    @Autowired
    private SmtpMailSender smtpMailSender;

    @RequestMapping("/login")
    public String login() {
        return "profilUser";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        authContext.getUser().setIsonline("N");
        userRepository.save(authContext.getUser());
        authContext.setUser(null);
        session.removeAttribute("user");

        // on retire toutes les cookies utilisé pour facilité l'utilisation du user ..
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setValue("");
                cookies[i].setPath("/");
                cookies[i].setMaxAge(0);
                resp.addCookie(cookies[i]);
            }


        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("authUser", authContext.getUser());
        }
        return "redirect:/";
    }

    @RequestMapping("/connexion")
    public String connexion(HttpSession session) {
        return "redirect:#openModalConnexion";
    }

    @RequestMapping(value = "/connexionNew", method = RequestMethod.GET)
    public String connexionNew() {
        return "/#openModalConnexion";
    }

    @RequestMapping("/resetpassword")
    public String resetpassword(@RequestParam("iduser") String iduser) throws MessagingException {
        if (customUserRepository.checkUserExistance(iduser) == BigInteger.ONE) { // On vérifie si l'utilisateur existe
            System.out.println("L'utilisateur " + iduser + " existe!");
            UsersEntity user = customUserRepository.findUserById(iduser); // On trouve le user par iduser
            customUserRepository.updateUserPassword(user); // On met un mot de passe temporaire à l'utilisateur
            // On envoit un message lui disant son nouveau mot de passe
            smtpMailSender.send(user.getEmail(), "Troké : Changement de mot de passe",
                    "Bonjour " + user.getFirstname() + " " + user.getLastname() + ",<br/>" +
                            "Vous mot de passe a été réinitialisé.<br/>" +
                            "Votre nouveau mot de passe est: " + user.getPass() + "<br/>" +
                            "<a href='http://troke.me'>Retour à Troké!</a>"
            );

        } else {
            System.out.println("L'utilisateur " + iduser + " n'existe pas!");
        }
        return "redirect:#openModalConnexion";
    }

    @RequestMapping("/openModalConnexion")
    public String openModalConnexion(HttpSession session,
                                     @CookieValue(value = "Connect", defaultValue = "empty") String userCookie) {
        session.removeAttribute("error");
        if (!userCookie.equals("empty")) {
            UsersEntity user = userRepository.findUsersEntityByIduser(userCookie);
            session.setAttribute("user", user);
            authContext.setUser(user);
            return "redirect:/";
        }
        return "redirect:#openModalConnexion"; // FIXME la page refresh au moment du click
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String login(@RequestParam("iduser") String idUser,
                        @RequestParam("pass") String pass,
                        @RequestParam(value = "StayConnected", defaultValue = "notStay") String stay,
                        Model model,
                        HttpSession session, HttpServletResponse response) {
        UsersEntity user = userRepository.findUsersEntityByIduserAndPass(idUser, pass);
        //session.setAttribute("user", user);

        // si l'utilisateur coche il restera logué a son retour ..
        if (stay.equals("Stay")) {
            response.addCookie(new Cookie("Connect", user.getIduser()));
        }

        if (idUser.isEmpty()) {
            session.setAttribute("error", "* Veuillez entrer un nom d'utilisateur");
            // TODO THYMELEAF HACK
            if (false) {
                WebContext context = new WebContext(null, null, null);
                context.setVariable("user", user);
                context.setVariable("error", "* Veuillez entrer un nom d'utilisateur");
            }
            return "redirect:/connexion";
        } else if (pass.isEmpty()) {
            session.setAttribute("error", "* Veuillez entrer un mot de passe");
            // TODO THYMELEAF HACK
            if (false) {
                WebContext context = new WebContext(null, null, null);
                context.setVariable("user", user);
                context.setVariable("error", "* Veuillez entrer un nom d'utilisateur");
            }
            return "redirect:/connexion";
        } else if (user == null) {
            session.setAttribute("error", " * Le nom d'utilisateur ou le mot de passe est invalide !");
            // TODO THYMELEAF HACK
            if (false) {
                WebContext context = new WebContext(null, null, null);
                context.setVariable("user", user);
                context.setVariable("error", "* Veuillez entrer un nom d'utilisateur");
            }
            return "redirect:/connexion";
        } else {
            //userRepository.save(user);
            authContext.setUser(user);
            session.setAttribute("user", user);
            return "redirect:/";
        }

    }
}
