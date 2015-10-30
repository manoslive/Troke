package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomObjectRepository;
import com.tilf.troke.repository.ObjectRepository;
import com.tilf.troke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpSession;
import java.util.Calendar;

/**
 * Created by Alex on 2015-10-23.
 */
@Controller
public class ProfilController {


    @Autowired
    private ObjectRepository objectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthUserContext authContext;

    @Autowired
    private CustomObjectRepository customObjectRepository;


    @RequestMapping(value="/addObject", method= RequestMethod.POST)
    public String addObjectInventory(@ModelAttribute("object")ObjectsEntity object,
                                     @RequestParam("Name") String Name,
                                     @RequestParam("Description") String Description,
                                     @RequestParam("Valeur") int valeur,
                                     @RequestParam("rating") int rating) {
        java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        // donnée qui vien du formulaire ...
        object.setNameObject(Name);
        object.setDescObject(Description);
        object.setValueObject(valeur);
        object.setQuality(rating);
        object.setIduser(authContext.getUser().getIduser());
        object.setCreationdate(now);

        // donnée rentrer a la main pour des valeur par defaut ...
        object.setGuid("12345qwerty");
        object.setIdsubcategory(1);
        object.setRateable("N");
        object.setIssignaled("N");
        objectRepository.save(object);
        // id du dernier object créer pour pouvoir créer des objectImages ..
        int numObject = object.getIdobject();

        return "redirect:/profil";
    }

    // pour changer le password
    @RequestMapping(value="/passwordChange", method=RequestMethod.POST)
    public String changePassword(@RequestParam ("old_password") String old,
                                 @RequestParam("new_password") String passnew,
                                 @RequestParam("confirm_password") String confirmpass,
                                 HttpSession session ) {
        UsersEntity userToChange = authContext.getUser();
        if(passnew.equals(confirmpass) && old.equals(userToChange.getPass())) {
            // si le password est bon ou update le user courrant dans la method
            // , on save la BD, ensuite on update le context
            userToChange.setPass(passnew);
            userRepository.save(userToChange);
            authContext.setUser(userToChange);
            return "redirect:/profil";
        }
        else {
            // s'il y a des erreur au niveau de l'ancien password ou des confirmation ..
            session.setAttribute("errorPassword", "*Les mot de passe ne concorde pas ou l'ancien mot de passe n'est pas bon");
            return "redirect:/profil#openModalPassword";}
    }

    @RequestMapping(value="/openModalPassword", method=RequestMethod.GET)
    public String changePasswordNew(HttpSession session) {
        session.removeAttribute("errorPassword");
        return "redirect:/profil#openModalPassword";
    }

    @RequestMapping(value="/UpdateUser" ,method = RequestMethod.POST)
    public String UserUpdate(@RequestParam("profile-prenom") String username,
                             @RequestParam("profile-nomfamille") String lastname,
                             @RequestParam("profile-telephone") String telephone,
                             @RequestParam("profile-codepostal") String codepostal,
                             @RequestParam("profile-email") String email)
    {
        UsersEntity userActif = authContext.getUser();

        userActif.setFirstname(username);
        userActif.setLastname(lastname);
        userActif.setEmail(email);
        userActif.setTelephone(telephone);
        userActif.setZipcode(codepostal);

        userRepository.save(userActif);

        authContext.setUser(userActif);
        return "redirect:/profil";
    }

    @RequestMapping(value="/deleteObject", method = RequestMethod.POST)
    public String Objectdelete(HttpSession session)
    {
        String id = (String)session.getAttribute("idObjectDelete");

        ObjectsEntity objectToDelete = customObjectRepository.getObjectEntityByIdObject(Integer.parseInt(id));
        objectRepository.delete(objectToDelete);
        session.removeAttribute("idObjectDelete");
        return "redirect:/profil";
    }

    @RequestMapping(value="/openModalDelete", method = RequestMethod.POST)
    public String modalDeleteOpen(Model model,
                                  @RequestParam("idobject")String id_object,
                                  HttpSession session)
    {
        session.setAttribute("idObjectDelete", id_object);

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("idObjectDelete", id_object);

        }
            return "redirect:/profil#openModalDelete";
    }
}
