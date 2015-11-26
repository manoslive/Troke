package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.ImageobjectEntity;
import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.*;
import com.tilf.troke.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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
    private CustomUserRepository customUserRepository;

    @Autowired
    private CustomObjectRepository customObjectRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageObjectRepository imageObjectRepository;

    @Autowired
    private CustomImageObjectRepository customImageObjectRepository;





    @RequestMapping(value="/addObject", method= RequestMethod.POST)
    public String addObjectInventory(@RequestParam("Name") String Name,
                                     @RequestParam("Description") String Description,
                                     @RequestParam("Valeur") int valeur,
                                     @RequestParam("rating") int rating,
                                     @RequestParam("catCombo") int categorie,
                                     @RequestParam(value= "photoMain", required=false) MultipartFile mainPhoto,
                                     @RequestParam(value= "photo1", required=false) MultipartFile photo1,
                                     @RequestParam(value= "photo2", required=false) MultipartFile photo2,
                                     @RequestParam(value= "photo3", required=false) MultipartFile photo3,
                                     HttpSession session)
    {
        java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        MultipartFile[] images = {mainPhoto, photo1, photo2, photo3};

        boolean imageIsUploaded;
        ImageobjectEntity photo = new ImageobjectEntity();
        int lastID;
        String firstChar;
        ObjectsEntity object = new ObjectsEntity();


        if(session.getAttribute("ObjectToModify") == null)
        {
            // donnée qui vien du formulaire ...
            object.setNameObject(Name);
            object.setDescObject(Description);
            object.setValueObject(valeur);
            object.setQuality(rating);
            object.setIduser(authContext.getUser().getIduser());
            object.setCreationdate(now);
            object.setIdsubcategory(categorie);

            // donnée rentrer a la main pour des valeur par defaut ...
            object.setGuid("no_avatar.png");

            object.setRateable("N");
            object.setIssignaled("N");
            

            // ajout a la BD
            objectRepository.save(object);
            // on va chercher le dernier ID ...
            lastID = object.getIdobject();

            // on ajoute ensuite les 4 photo dans la table ImageObject
            for(int i = 0; i < 4; i++) {
                // on set le idObject du dernier object ajouter ...
                photo.setIdobject(lastID);

                // On génère le nom unique de l'image et on vérifie qu'il
                // n'existe pas déjà.
                String imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
                while (customUserRepository.checkAvatarName(imageName) == BigInteger.ONE) {
                    imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
                }
                if(images[i] != null){
                    // ici on upload l'image sur le serveur avec le bon nom ..
                    imageIsUploaded = imageService.uploadImage(images[i], imageName, true, session);
                    if (imageIsUploaded) {
                        photo.setGuidimage(imageName);
                    }
                    else
                    {
                        photo.setGuidimage("*"+ imageName);
                    }
                }
               if(i == 0)
               {
                   photo.setIsmain("Main");
               }
                else
               {
                   photo.setIsmain("NotMain");
               }
                firstChar = photo.getGuidimage().substring(0,1);
                imageObjectRepository.save(photo);
            }


        }
        else
        {
            object = (ObjectsEntity)session.getAttribute("ObjectToModify");

            object.setNameObject(Name);
            object.setDescObject(Description);
            object.setValueObject(valeur);
            object.setQuality(rating);
            objectRepository.save(object);
            session.removeAttribute("ObjectToModify");
            session.removeAttribute("listedimage");

            // TODO
            // aller chercher les 4 images objects par rapport a l'object et les saver aussi avec les nouveaux parametres ..
        }

        return "redirect:/profilinv";
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
    public String UserUpdate(HttpSession session,
                             @RequestParam("profile-prenom") String username,
                             @RequestParam("profile-nomfamille") String lastname,
                             @RequestParam("profile-telephone") String telephone,
                             @RequestParam("profile-codepostal") String codepostal,
                             @RequestParam("profile-email") String email,
                             @RequestParam(value= "avatarProfil", required=false) MultipartFile avatar)
    {
        // valeur par defaut d'une image vide ..
        String defaultImage = "no_avatar.jpg";

        // On génère le nom unique de l'image et on vérifie qu'il
        // n'existe pas déjà.
        String imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        while (customUserRepository.checkAvatarName(imageName) == BigInteger.ONE) {
            imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        }

        // instantiation du user avec le user loggé..
        UsersEntity userActif = authContext.getUser();

        // Vérification du courriel (1 si existe, 0 sinon)
        if(email.equals(authContext.getUser().getEmail()) || customUserRepository.checkEmailExistance(email) == BigInteger.ZERO ) {
            userActif.setEmail(email);
            // on set les nouveaux parametre modifier dans le profil ..
            userActif.setFirstname(username);
            userActif.setLastname(lastname);
            userActif.setTelephone(telephone);
            userActif.setZipcode(codepostal);

            // ici on upload l'image sur le serveur avec le bon nom ..
            boolean imageIsUploaded = imageService.uploadImage(avatar, imageName, true, session);

            if (imageIsUploaded) {
                userActif.setAvatar(imageName);
                session.setAttribute("avatarpathProfil", imageName);
            } else {
                userActif.setAvatar(defaultImage);
                session.setAttribute("avatarpathProfil", defaultImage);
            }
            // on save le user dans la BD..
            userRepository.save(userActif);
        }

        // on re set le user loggé avec celui modifié ..
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
        return "redirect:/profilinv";
    }

    @RequestMapping(value="/openModalDelete", method = RequestMethod.POST)
    public String modalDeleteOpen(@RequestParam("idObjectDelete")String id_object,
                                  HttpSession session)
    {
        session.setAttribute("idObjectDelete", id_object);

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("idObjectDelete", id_object);

        }
            return "redirect:/profilinv#openModalDelete";
    }

    @RequestMapping(value="/openModalModifier", method = RequestMethod.POST)
    public String modalModifierOpen(@RequestParam("idObjectModifier") String id_object,
                                    HttpSession session,
                                    Model model)
    {
        // je catch l'id de l'object a modifier par le Request param et ensuite je récupere l'object au complet
        ObjectsEntity objectToModify = customObjectRepository.getObjectEntityByIdObject(Integer.parseInt(id_object));

        // j'ajoute un objet de session pour le récuperer dans le modal d'ajout et remplir les bon champs.
        session.setAttribute("ObjectToModify", objectToModify);

        List<ImageobjectEntity> listedimage;

        listedimage = customImageObjectRepository.getImageObjectbyObjectId(objectToModify.getIdobject());
        session.setAttribute("listedimage", listedimage);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("ObjectToModify", id_object);
            context.setVariable("listedimage", listedimage);

        }
        return "redirect:/profilinv#openModalAjouter";
    }

    @RequestMapping(value="/closeAjouter", method = RequestMethod.GET)
    public String closeModalAjouter(HttpSession session)
    {
        session.removeAttribute("ObjectToModify");
        session.removeAttribute("listedimage");
        return "redirect:/profilinv";
    }
    @RequestMapping(value="/openModalAjouter", method= RequestMethod.GET)
    public String openModalAjouter(HttpSession session)
    {


        session.removeAttribute("ObjectToModify");
        session.removeAttribute("listedimage");



        return "redirect:/profilinv#openModalAjouter";
    }
}
