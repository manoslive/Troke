package com.tilf.troke.service;

import com.tilf.troke.entity.CustomSearchObjectEntity;
import com.tilf.troke.entity.ImageobjectEntity;
import com.tilf.troke.entity.ObjectsEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Manu on 2015-11-26.
 */

@Service
public class ObjectService {

    public CustomSearchObjectEntity convertObjectEntityInCustomSearchObjectEntity(ObjectsEntity obj, List<ImageobjectEntity> imageList){
        CustomSearchObjectEntity customObjet = new CustomSearchObjectEntity();
        customObjet.setIduser(obj.getIduser());
        customObjet.setCreationdate(obj.getCreationdate());
        customObjet.setDescObject(obj.getDescObject());
        customObjet.setIdobject(obj.getIdobject());
        customObjet.setIdsubcategory(obj.getIdsubcategory());
        customObjet.setIssignaled(obj.getIssignaled());
        customObjet.setNameObject(obj.getNameObject());
        customObjet.setQuality(obj.getQuality());
        customObjet.setRateable(obj.getRateable());
        customObjet.setValueObject(obj.getValueObject());
        customObjet.setImage1(imageList.get(0).getGuidimage());
        if(imageList.get(1).getIsmain().length() > 1) {customObjet.setImage2("LogoWorld_Transparence.png");}
        else {customObjet.setImage2(imageList.get(1).getGuidimage());}

        if(imageList.get(2).getIsmain().length() > 1){customObjet.setImage3("LogoWorld_Transparence.png");}
        else {customObjet.setImage3(imageList.get(2).getGuidimage());}

        if(imageList.get(3).getIsmain().length() > 1) {customObjet.setImage4("LogoWorld_Transparence.png");}
        else {customObjet.setImage4(imageList.get(3).getGuidimage());}
        return customObjet;
    }
}
