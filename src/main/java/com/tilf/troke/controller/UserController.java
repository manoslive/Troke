package com.tilf.troke.controller;

import com.tilf.troke.domain.UserSignupForm;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomUserRepository;
import com.tilf.troke.repository.UserRepository;
import com.tilf.troke.service.ImageService;
import com.tilf.troke.service.SmtpMailSender;
import com.tilf.troke.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.UUID;

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

    @Autowired
    private ImageService imageService;

    @Autowired
    private SmtpMailSender smtpMailSender;

    @ExceptionHandler(Throwable.class)
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String adduser(@ModelAttribute("userSignupForm") @Valid UserSignupForm userSignupForm,
                          BindingResult result,
                          @RequestParam(value = "avatar", required = false) @Valid MultipartFile avatar,
                          BindingResult result2,
                          RedirectAttributes redirectAttributes,
                          Model model, HttpSession session) throws MessagingException{
        // On valide le model userSignupForm
        userValidator.validate(userSignupForm, result);
        // La date d'aujourd'hui pour la création de l'utilisateur
        java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        // Vérification du username (1 si existe, 0 sinon)
        BigInteger checkUserExistance = customUserRepository.checkUserExistance(userSignupForm.getIduser());
        // Vérification du courriel (1 si existe, 0 sinon)
        BigInteger checkEmailExistance = customUserRepository.checkEmailExistance(userSignupForm.getEmail());
        // Image par défaut
        String defaultImage = "no_avatar.jpg";
        // On génère le nom unique de l'image et on vérifie qu'il
        // n'existe pas déjà.
        String imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        while (customUserRepository.checkAvatarName(imageName) == BigInteger.ONE) {
            imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        }

        // On vérifie qu'il n'y a pas d'erreur, que l'utilisateur est unique ainsi que le courriel
        if (!result.hasErrors() && checkUserExistance == BigInteger.ZERO && checkEmailExistance == BigInteger.ZERO) {
            // On se déclare un nouveau UsersEntity pour l'ajout à la BD
            UsersEntity user = new UsersEntity();


            // Gestion de l'upload de l'image non temporaire
            boolean imageIsUploaded = imageService.uploadImage(avatar, imageName, true, session);

            if (imageIsUploaded) {
                if (session.getAttribute("tempimage") != null) {
                    imageName = session.getAttribute("tempimage").toString();
                }
                user.setAvatar(imageName);
                session.setAttribute("avatarpath", imageName);
            } else {
                user.setAvatar(defaultImage);
                session.setAttribute("avatarpath", defaultImage);
            }

            // On affecte chacun des champs
            user.setIduser(userSignupForm.getIduser());
            user.setFirstname(userSignupForm.getFirstname());
            user.setLastname(userSignupForm.getLastname());
            user.setPass(userSignupForm.getPass());
            user.setEmail(userSignupForm.getEmail());
            user.setTelephone(userSignupForm.getTelephone());
            user.setZipcode(userSignupForm.getZipcode());
            user.setState("N");
            user.setIsonline("N");
            user.setCreationdate(now);
            user.setPermissionlevel(0);
            user.setIsvip("N");
            // On insert dans la BD
            userRepository.save(user);
            // Envoie de courriel pour avertir l'utilisateur du nouveau compte créé
            smtpMailSender.send(userSignupForm.getEmail(), "Bienvenue chez Troké", "Bonjour " + userSignupForm.getFirstname() + " " + userSignupForm.getLastname() + ", <br/>" +
                    " Vous êtes maintenant inscrit sur Troké.<br/> " +
                    " <a href='http://www.troke.me'>Comment à faire du troc</a>");
            // On retour à la page home
            return "forward:/";
        }
        // Ajout d'une image temporaire pour permettre le réaffichage
        if (imageService.uploadImage(avatar, imageName, false, session)) {
            session.setAttribute("tempimage", imageName);
        }

        // Ajout manuel de l'erreur de l'utilisateur déjà utilisé
        if (checkUserExistance == BigInteger.ONE) {
            ObjectError error = new ObjectError("error.iduser", "Le nom d'usager est déjà utilisé.");
            result.addError(error);
        }
        // Ajout manuel de l'erreur du courriel déjà utilisé
        if (checkEmailExistance == BigInteger.ONE) {
            ObjectError error = new ObjectError("error.email", "Le courriel est déjà utilisé.");
            result.addError(error);
        }

        // Les flash attributes servent à permettre l'obtention de la
        // liste d'erreurs
        redirectAttributes.addFlashAttribute("userSignupForm", userSignupForm);
        redirectAttributes.addFlashAttribute("fields", result);

        // On envoit le data pour que l'image choisie reste choisie
        session.setAttribute("avatarfile", avatar);

        // On met les infos du formulaire dans une variable de session
        // pour permettre de garder les informations du formulaire
        // lors d'une erreur
        session.setAttribute("userInformation", userSignupForm);

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("fields", result);
            context.setVariable("userSignupForm", userSignupForm);
            context.setVariable("userInformation", userSignupForm);
            context.setVariable("tempimage", userSignupForm);
            context.setVariable("avatarfile", avatar);
        }
        // On retourne au formulaire pcq il y a des erreurs
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