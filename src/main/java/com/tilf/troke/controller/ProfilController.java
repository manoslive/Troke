package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;

/**
 * Created by Alex on 2015-10-23.
 */
@Controller
public class ProfilController {


    @Autowired
    private ObjectRepository objectRepository;
    @Autowired
    private AuthUserContext authContext;

    @RequestMapping(value="/addObject", method= RequestMethod.POST)
    public String addObjectInventory(@ModelAttribute("object")ObjectsEntity object,
                                     @RequestParam("Name") String Name,
                                     @RequestParam("Description") String Description,
                                     @RequestParam("Valeur") int valeur,
                                     @RequestParam("rating") int rating)
    {
        java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        object.setNameObject(Name);
        object.setDescObject(Description);
        object.setGuid("12345qwerty");
        object.setIdsubcategory(1);
        object.setValueObject(valeur);
        object.setQuality(rating);
        object.setIduser(authContext.getUser().getIduser());
        object.setRateable("N");
        object.setIssignaled("N");
        object.setCreationdate(now);

        objectRepository.save(object);


        return "forward:/";
    }


}
