package com.tilf.troke.controller;

import com.tilf.troke.domain.UserSignupForm;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomUserRepository;
import com.tilf.troke.repository.UserRepository;
import com.tilf.troke.service.ImageService;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
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

    @ExceptionHandler(Throwable.class)
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String adduser(@ModelAttribute("userSignupForm") @Valid UserSignupForm userSignupForm, BindingResult result, @RequestParam(value = "avatar", required = false) @Valid MultipartFile avatar, BindingResult result2, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        userValidator.validate(userSignupForm, result);
        java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        BigInteger checkUserExistance = customUserRepository.checkUserExistance(userSignupForm.getIduser());
        BigInteger checkEmailExistance = customUserRepository.checkEmailExistance(userSignupForm.getEmail());
        String imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        String imagePath = "src/main/resources/static/uploaded-images/";
        String defaultImage = "no_avatar.jpg";

        if (!result.hasErrors() && checkUserExistance == BigInteger.ZERO && checkEmailExistance == BigInteger.ZERO) {
            UsersEntity user = new UsersEntity();

            if (avatar != null) {
                if (!avatar.isEmpty()) {
                    try {
                        byte[] bytes = avatar.getBytes();
                        byte[] resizedBytes;
                        // Vérification de la taille de l'image
                        InputStream in = new ByteArrayInputStream(bytes);

                        BufferedImage buf = ImageIO.read(in);
                        float width = (float)buf.getWidth();
                        float height = (float)buf.getHeight();
                        float calculHeight = 800 / (width / height);
                        int heightModifier = (int)Math.ceil(calculHeight);
                        if (width / height != 1) {
                            // Resize byte
                            resizedBytes = imageService.scale(bytes, 800 , heightModifier);
                        } else {
                            // Resize byte
                            resizedBytes = imageService.scale(bytes, 800, 800);
                        }

                        BufferedOutputStream stream =
                                new BufferedOutputStream(new FileOutputStream(new File(imagePath + imageName)));
                        stream.write(resizedBytes);
                        stream.close();
                        user.setAvatar(imageName);
                        session.setAttribute("avatarpath", imageName);
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                        System.out.println(ioe.fillInStackTrace());
                    }
                }
            } else {
                user.setAvatar(defaultImage);
                session.setAttribute("avatarpath", defaultImage);

            }
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
            userRepository.save(user);
            return "forward:/";
        }
        if (checkUserExistance == BigInteger.ONE) {
            ObjectError error = new ObjectError("error.iduser", "Le nom d'usager est déjà utilisé.");
            result.addError(error);
        }
        if (checkEmailExistance == BigInteger.ONE) {
            ObjectError error = new ObjectError("error.email", "Le courriel est déjà utilisé.");
            result.addError(error);
        }
        redirectAttributes.addFlashAttribute("userSignupForm", userSignupForm);
        redirectAttributes.addFlashAttribute("fields", result);
        session.setAttribute("userInformation", userSignupForm);
        if (avatar != null) {
            session.setAttribute("avatarpath", imageName);
        } else {
            session.setAttribute("avatarpath", defaultImage);
        }

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("fields", result);
            context.setVariable("userSignupForm", userSignupForm);
            context.setVariable("userInformation", userSignupForm);
            context.setVariable("avatarpath", defaultImage);
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