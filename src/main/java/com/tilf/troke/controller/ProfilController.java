package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.domain.UserProfil;
import com.tilf.troke.entity.ImageobjectEntity;
import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.*;
import com.tilf.troke.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
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
        ObjectsEntity object = new ObjectsEntity();
        List<ImageobjectEntity> listeImage = new ArrayList<>();
        String firstChar;


        if(session.getAttribute("ObjectToModify") == null)
        {
            // donnée qui vien du formulaire ...
            object.setNameObject(Name); // nom de l'objet recu du formulaire ..
            object.setDescObject(Description);// description de l'objet ou service ..
            object.setValueObject(valeur); // valeur de l'objet décidé par le user recu du formulaire ..
            object.setQuality(rating); // on set le rating recu de formulaire avec les étoiles ..
            object.setIduser(authContext.getUser().getIduser()); // on set le id du user a celui qui est loggé dans la session ..
            object.setCreationdate(now); // on met la date de création à maintenant ..
            object.setIdsubcategory(categorie); // on set le id de la category sur l'objet recu du formulaire

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
                        if(i == 0)
                        {
                            photo.setIsmain("Y");
                        }
                        else
                        {
                            photo.setIsmain("N");
                        }
                    }
                    else
                    {
                        photo.setGuidimage(imageName);
                        if(i == 0)
                        {
                            photo.setIsmain("Y*");
                        }
                        else
                        {
                            photo.setIsmain("N*");
                        }
                    }
                }

                // firstChar = photo.getGuidimage().substring(0,1); // avoir le premier char de la string de l'image ..
                imageObjectRepository.save(photo);
            }
        }
        else
        {
            object = (ObjectsEntity)session.getAttribute("ObjectToModify");
            listeImage = (List<ImageobjectEntity>)session.getAttribute("listedimage");

            object.setNameObject(Name); // on reset le nom du service ou objet en cas que le user l'aie modifié
            object.setDescObject(Description);// on reset la description en cas que le user l'aie modifié ..
            object.setValueObject(valeur); // on re set la value ..
            object.setQuality(rating); // on re set la quality
            object.setIdsubcategory(categorie); // on re set la category ..

            // on ajoute ensuite les 4 photo dans la table ImageObject
            for(int i = 0; i < 4; i++)
            {

                photo.setGuidimage(listeImage.get(i).getGuidimage());
                String ImageName = images[i].getOriginalFilename();
                if(!images[i].isEmpty())
                {
                    // ici on upload l'image sur le serveur avec le bon nom ..
                    imageIsUploaded = imageService.uploadImage(images[i],listeImage.get(i).getGuidimage() , true, session);
                    firstChar = listeImage.get(i).getIsmain().substring(1,listeImage.get(i).getIsmain().length());
                    if (imageIsUploaded && listeImage.get(i).getIsmain().substring(0,1).equals("*")) {
                        photo.setIsmain(firstChar);
                        photo.setIdobject(listeImage.get(i).getIdobject());
                        imageObjectRepository.save(photo);
                    }
                }

            }

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



    @RequestMapping(value="/profil", method= RequestMethod.GET)
    public String GetProfil(UserProfil userProfil, HttpSession session, Model model)
    {
        // on get le user actif pour voir s'il est logué ..
        UsersEntity user = authContext.getUser();

        // si le user est logué on remplit le userprofil avec les bonne infos
        if(user != null) {
            userProfil.setFirstname(user.getFirstname());
            userProfil.setTelephone(user.getTelephone());
            userProfil.setEmail(user.getEmail());
            userProfil.setZipcode(user.getZipcode());
            userProfil.setLastname(user.getLastname());
            userProfil.setAvatar(user.getAvatar());

            model.addAttribute("UserActif", user);

            // TODO THYMELEAF HACK
            if (false) {
                WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                context.setVariable("UserActif", user);


            }
            return "fragments/profil/pageprofil";
        }
        // sinon on le renvoit a la connexion ..
        else
        {
            session.removeAttribute("error");
            return "redirect:/openModalConnexion";
        }
    }

    @RequestMapping(value="/profil" , method = RequestMethod.POST)
    public String UpdateProfil(@Valid UserProfil userProfil,BindingResult bindingResult,
                               @RequestParam(value= "avatarProfil", required=false) MultipartFile avatar,
                               Model model,HttpSession session
                               )
    {

        // s'il y a des erreurs dans le profil ..
        if(bindingResult.hasErrors())
        {
            UsersEntity userActif = authContext.getUser();
            model.addAttribute("UserActif", userActif);
            return "fragments/profil/pageprofil";

        }
        // si aucune erreur on commence le processus d'update du user ..
        else
        {
            // on get une instance du useractif pour le modifier
            UsersEntity userActif = authContext.getUser();


            if(userProfil.getEmail().equals(authContext.getUser().getEmail()) ||
                    customUserRepository.checkEmailExistance(userProfil.getEmail()) == BigInteger.ZERO )
            {
                // on modifie l'instance du user avec les modification ..
                userActif.setFirstname(userProfil.getFirstname());
                userActif.setLastname(userProfil.getLastname());
                userActif.setTelephone(userProfil.getTelephone());
                userActif.setZipcode(userProfil.getZipcode());
                userActif.setEmail(userProfil.getEmail());

                // on verifie si l'avatar a été modifié
                if(!avatar.isEmpty()) {
                    // ici on upload l'image sur le serveur avec le bon nom ..
                    // On génère le nom unique de l'image et on vérifie qu'il
                    // n'existe pas déjà.
                    if(userActif.getAvatar() == null || userActif.getAvatar().equals("no_avatar.jpg"))
                    {
                        String imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
                        while (customUserRepository.checkAvatarName(imageName) == BigInteger.ONE)
                        {
                            imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
                        }
                        boolean imageIsUploaded = imageService.uploadImage(avatar, imageName, true, session);
                        userActif.setAvatar(imageName);
                        session.setAttribute("avatarpathProfil", imageName);
                    }
                    // aussi non on remplace l'image sur le serveur par la nouvelle
                    else
                    {
                        boolean imageIsUploaded = imageService.uploadImage(avatar, userActif.getAvatar(), true, session);
                        session.setAttribute("avatarpathProfil", userActif.getAvatar());
                    }
                }
                // update du context ..
                authContext.setUser(userActif);

                // on save dans la BD
                userRepository.save(userActif);

            }
        }
        return "redirect:/profil";
    }


    @RequestMapping(value="/deleteObject", method = RequestMethod.POST)
    public String Objectdelete(HttpSession session)
    {
        String id = (String)session.getAttribute("idObjectDelete");

        ObjectsEntity objectToDelete = customObjectRepository.getObjectEntityByIdObject(Integer.parseInt(id));
        objectRepository.delete(objectToDelete);
        session.removeAttribute("idObjectDelete");
        session.removeAttribute("NameObject");
        return "redirect:/profilinv";
    }

    @RequestMapping(value="/openModalDelete", method = RequestMethod.POST)
    public String modalDeleteOpen(@RequestParam("idObjectDelete")String id_object,
                                  HttpSession session,
                                  Model model)
    {
        session.setAttribute("idObjectDelete", id_object);
        String NameObject = customObjectRepository.getObjectNameByItemID(Integer.parseInt(id_object));
        session.setAttribute("NameObject", NameObject);

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("idObjectDelete", id_object);
            context.setVariable("NameObject", NameObject);

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
