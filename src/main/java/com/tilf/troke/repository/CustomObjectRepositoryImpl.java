package com.tilf.troke.repository;

import com.tilf.troke.entity.CustomObjetImageEntity;
import com.tilf.troke.entity.CustomSearchObjectEntity;
import com.tilf.troke.entity.ImageobjectEntity;
import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Manu on 2015-10-19.
 */
@Repository
public class CustomObjectRepositoryImpl implements CustomObjectRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectService objectService;


    // Requêtes sur les catégories
    @Override
    public List<String> getAllCategories() {
        String query = "select NAME_CATEGORY from category";
        Query queryObject = entityManager.createNativeQuery(query);
        List<String> categoryList = (List<String>) queryObject.getResultList();

        return categoryList;
    }

    @Override
    public List<String> getAllSubCategories(String categoryName) {
        int number = 11;
        //BigInteger bi = BigInteger.valueOf(number);

        String query = "select NAME_SUBCATEGORY from subcategory where IDCATEGORY=:number1";
        Query queryObject = entityManager.createNativeQuery(query);
        number = getIdCategoryFromCategoryName(categoryName);
        queryObject.setParameter("number1", number);

        List<String> subcategoryList = (List<String>) queryObject.getResultList();

        return subcategoryList;
    }

    // Requêtes sur les objects
    @Override
    public List<CustomSearchObjectEntity> getRecentItems() {
        TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity as o order by o.creationdate desc", ObjectsEntity.class);
        List<ObjectsEntity> result = query.setMaxResults(36).getResultList();

        List<CustomSearchObjectEntity> objects = new ArrayList<>();

        //Ajout des objets + images dans un custom Entity
        for(int i=0; i< result.size(); i++) {
            CustomSearchObjectEntity customObjet = new CustomSearchObjectEntity();
            customObjet.setIduser(result.get(i).getIduser());
            customObjet.setCreationdate(result.get(i).getCreationdate());
            customObjet.setDescObject(result.get(i).getDescObject());
            customObjet.setIdobject(result.get(i).getIdobject());
            customObjet.setIdsubcategory(result.get(i).getIdsubcategory());
            customObjet.setIssignaled(result.get(i).getIssignaled());
            customObjet.setNameObject(result.get(i).getNameObject());
            customObjet.setQuality(result.get(i).getQuality());
            customObjet.setRateable(result.get(i).getRateable());
            customObjet.setValueObject(result.get(i).getValueObject());

            //Get tous les images
            String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain desc, guidimage";
            Query queryObject2 = entityManager.createQuery(query2);
            queryObject2.setParameter("idObject", result.get(i).getIdobject());
            List<ImageobjectEntity> LImages = (List<ImageobjectEntity>) queryObject2.getResultList();

            // Ajout de chacune des images dans l'objet
            customObjet.setImage1(LImages.get(0).getGuidimage());
            if(LImages.get(1).getIsmain().length() > 1) {customObjet.setImage2("LogoWorld_Transparence.png");}
            else {customObjet.setImage2(LImages.get(1).getGuidimage());}

            if(LImages.get(2).getIsmain().length() > 1){customObjet.setImage3("LogoWorld_Transparence.png");}
            else {customObjet.setImage3(LImages.get(2).getGuidimage());}

            if(LImages.get(3).getIsmain().length() > 1) {customObjet.setImage4("LogoWorld_Transparence.png");}
            else {customObjet.setImage4(LImages.get(3).getGuidimage());}

            // Ajout du nom de sous-catégorie
            customObjet.setSubCategoryName(getSubCatNameBySubCatId(result.get(i).getIdsubcategory()));

            objects.add(customObjet);
        }

        return objects;
    }

    @Override
         public List<Integer> getCatIdListFromCatNameSet(Set<String> catNameList) {
        List<Integer> catIdList = new ArrayList<>();
        for (String catName : catNameList) {
            catIdList.add(getIdCategoryFromCategoryName(catName));
        }
        return catIdList;
    }

    @Override
    public List<Integer> getSubCatIdListFromSubCatNameSet(Set<String> subCatNameList) {
        List<Integer> catIdList = new ArrayList<>();
        for (String subCatName : subCatNameList) {
            catIdList.add(getIdSubCategoryFromSubCatName(subCatName));
        }
        return catIdList;
    }

    @Override
    public List<ObjectsEntity> getObjectsByCategory(Set<String> categoryName) {
        // TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity as o where exists (select s.idSubcategory from SubcategoryEntity s where o.idsubcategory=s.idSubcategory and s.idcategory in :idlist)", ObjectsEntity.class);
        Query query = entityManager.createNativeQuery("select IDOBJECT, NAME_OBJECT, DESC_OBJECT, guid, s.idSUBCATEGORY, VALUE_OBJECT, QUALITY, IDUSER, RATEABLE, ISSIGNALED, CREATIONDATE from objects o inner join subcategory s on s.IDSUBCATEGORY = o.IDSUBCATEGORY inner join category c on c.IDCATEGORY = s.IDCATEGORY where c.IDCATEGORY in :idlist", ObjectsEntity.class);
        // query.setParameter("idcategory", getIdCategoryFromCategoryName("à changer"));
        query.setParameter("idlist", getCatIdListFromCatNameSet(categoryName));
        List<ObjectsEntity> result = query.getResultList();

        return result;
    }

    @Override
    public List<CustomSearchObjectEntity> getObjectsBySubCategory(Set<String> subCategoryName) {
        // TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity as o where exists (select s.idSubcategory from SubcategoryEntity s where o.idsubcategory=s.idSubcategory and s.idcategory in :idlist)", ObjectsEntity.class);
        Query query = entityManager.createNativeQuery("select IDOBJECT, NAME_OBJECT, DESC_OBJECT, guid, idSUBCATEGORY, VALUE_OBJECT, QUALITY, IDUSER, RATEABLE, ISSIGNALED, CREATIONDATE from objects  where IDSUBCATEGORY in :idlist order by CREATIONDATE desc", ObjectsEntity.class);
        // query.setParameter("idcategory", getIdCategoryFromCategoryName("à changer"));
        query.setParameter("idlist", getSubCatIdListFromSubCatNameSet(subCategoryName));
        List<ObjectsEntity> result = query.getResultList();
        List<CustomSearchObjectEntity> objects = new ArrayList<>();
        //Ajout des objets + images dans un custom Entity
        for(int i=0; i< result.size(); i++){
            CustomSearchObjectEntity customObjet = new CustomSearchObjectEntity();
            customObjet.setIduser(result.get(i).getIduser());
            customObjet.setCreationdate(result.get(i).getCreationdate());
            customObjet.setDescObject(result.get(i).getDescObject());
            customObjet.setIdobject(result.get(i).getIdobject());
            customObjet.setIdsubcategory(result.get(i).getIdsubcategory());
            customObjet.setIssignaled(result.get(i).getIssignaled());
            customObjet.setNameObject(result.get(i).getNameObject());
            customObjet.setQuality(result.get(i).getQuality());
            customObjet.setRateable(result.get(i).getRateable());
            customObjet.setValueObject(result.get(i).getValueObject());

            //Get tous les images
            String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain desc, guidimage";
            Query queryObject2 = entityManager.createQuery(query2);
            queryObject2.setParameter("idObject", result.get(i).getIdobject());
            List<ImageobjectEntity> LImages = (List<ImageobjectEntity>) queryObject2.getResultList();

            // Ajout de chacune des images dans l'objet
            customObjet.setImage1(LImages.get(0).getGuidimage());
            if(LImages.get(1).getIsmain().length() > 1) {customObjet.setImage2("LogoWorld_Transparence.png");}
            else {customObjet.setImage2(LImages.get(1).getGuidimage());}

            if(LImages.get(2).getIsmain().length() > 1){customObjet.setImage3("LogoWorld_Transparence.png");}
            else {customObjet.setImage3(LImages.get(2).getGuidimage());}

            if(LImages.get(3).getIsmain().length() > 1) {customObjet.setImage4("LogoWorld_Transparence.png");}
            else {customObjet.setImage4(LImages.get(3).getGuidimage());}

            // Ajout du nom de sous-catégorie
            customObjet.setSubCategoryName(getSubCatNameBySubCatId(result.get(i).getIdsubcategory()));

            objects.add(customObjet);
        }

        return objects;
    }

    public int getIdCategoryFromCategoryName(String categoryName) {
        String query = "select distinct c.idcategory from category c inner join subcategory s on c.idcategory=s.idcategory where name_category=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", categoryName);
        int idcategory = (int) queryObject.getSingleResult();
        return idcategory;
    }

    public int getIdSubCategoryFromSubCatName(String subCatName){
        String query = "select distinct s.idSUBCATEGORY from subcategory s where NAME_SUBCATEGORY=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", subCatName);
        int idcategory = (int) queryObject.getSingleResult();
        return idcategory;
    }

    @Override
    public List<String> getSubCatListByCategoryName(String categoryname){
        List<String> subCatList;

        String query = "select s.NAME_SUBCATEGORY from category c inner join subcategory s on c.idcategory=s.idcategory where c.NAME_CATEGORY=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", categoryname);
        subCatList = (List<String>) queryObject.getResultList();
        return subCatList;
    }

    @Override
    public String getSubCatNameBySubCatId(int subCatId){
        String query = "select s.nameSubcategory from SubcategoryEntity s where s.idSubcategory=:idSubCat";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("idSubCat", subCatId);
        String subCatName = (String)queryObject.getSingleResult();

        return subCatName;
    }

    @Override
    public ObjectsEntity getObjectEntityByIdObject(int id_object) {
        String query = "select o from ObjectsEntity o where o.idobject=:idobject";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("idobject", id_object);
        ObjectsEntity obj = (ObjectsEntity) queryObject.getSingleResult();

        return obj;
    }

    @Override
    public CustomSearchObjectEntity getCustomsearchobjectentityByIdObject(int idObject){
        CustomSearchObjectEntity obj = objectService.convertObjectEntityInCustomSearchObjectEntity(getObjectEntityByIdObject(idObject), getObjectImageListByIdobject(idObject));
        obj.setSubCategoryName(getSubCatNameBySubCatId(obj.getIdsubcategory()));
        return obj;
    }

    @Override
    public CustomObjetImageEntity getCustomObjectImageEntityByIdObject(int id_object) {
        String query = "select o from ObjectsEntity o where o.idobject=:idobject";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("idobject", id_object);
        ObjectsEntity obj = (ObjectsEntity) queryObject.getSingleResult();

        CustomObjetImageEntity customObjet = new CustomObjetImageEntity();
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

        List<ImageobjectEntity> LImages = getObjectImageListByIdobject(id_object);

        customObjet.setImage1(LImages.get(0).getGuidimage());

        if(LImages.get(1).getIsmain().length() > 1) {customObjet.setImage2("LogoWorld_Transparence.png");}
        else {customObjet.setImage2(LImages.get(1).getGuidimage());}

        if(LImages.get(2).getIsmain().length() > 1){customObjet.setImage3("LogoWorld_Transparence.png");}
        else {customObjet.setImage3(LImages.get(2).getGuidimage());}

        if(LImages.get(3).getIsmain().length() > 1) {customObjet.setImage4("LogoWorld_Transparence.png");}
        else {customObjet.setImage4(LImages.get(3).getGuidimage());}

        return customObjet;
    }

    // Obtenir les images pour un ObjectEntity donné
    @Override
    public List<ImageobjectEntity> getObjectImageListByIdobject(int Idobject){
        String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain desc, guidimage";
        Query queryObject2 = entityManager.createQuery(query2);
        queryObject2.setParameter("idObject", Idobject);
        List<ImageobjectEntity> imageList = (List<ImageobjectEntity>) queryObject2.getResultList();

        return imageList;
    }

    // Recherches
    @Override
    public List<CustomSearchObjectEntity> getObjectListByKeyword(String keyword) {
        String query = "select o from ObjectsEntity o where UPPER(o.nameObject) like UPPER(:keyword)";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("keyword", "%" + keyword + "%");
        List<ObjectsEntity> objList = (List<ObjectsEntity>) queryObject.getResultList();
        List<CustomSearchObjectEntity> newObjList = new ArrayList<>();
        for(ObjectsEntity obj : objList) {
            CustomSearchObjectEntity newObj = objectService.convertObjectEntityInCustomSearchObjectEntity(obj, getObjectImageListByIdobject(obj.getIdobject()));
            newObj.setSubCategoryName(getSubCatNameBySubCatId(newObj.getIdsubcategory()));
            newObjList.add(newObj);
        }

        return newObjList;
    }

    //GetItemNameByID
    @Override
    public String getObjectNameByItemID(int itemID) {
        String query = "select o.nameObject from ObjectsEntity o where o.idobject = :itemID";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("itemID", itemID);
        String itemName = (String) queryObject.getSingleResult();
        return itemName;
    }

    //GetLesItems d'un inventaire selon le UserId
    @Override
    public List<CustomObjetImageEntity> getObjectsByUserID(int currentItemID, String userID) {
        List<CustomObjetImageEntity> objets = new ArrayList<>();

        //Get tous les items sauf le current selon le user ID
        String query = "select o from ObjectsEntity o where o.iduser = :userID and o.idobject != :currentItemID";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("currentItemID", currentItemID);
        queryObject.setParameter("userID", userID);
        List<ObjectsEntity> inventory = (List<ObjectsEntity>) queryObject.getResultList();

        //Ajout des objets + images dans un custom Entity
        for(int i=0; i< inventory.size(); i++){
            CustomObjetImageEntity customObjet = new CustomObjetImageEntity();
            customObjet.setIduser(inventory.get(i).getIduser());
            customObjet.setCreationdate(inventory.get(i).getCreationdate());
            customObjet.setDescObject(inventory.get(i).getDescObject());
            customObjet.setIdobject(inventory.get(i).getIdobject());
            customObjet.setIdsubcategory(inventory.get(i).getIdsubcategory());
            customObjet.setIssignaled(inventory.get(i).getIssignaled());
            customObjet.setNameObject(inventory.get(i).getNameObject());
            customObjet.setQuality(inventory.get(i).getQuality());
            customObjet.setRateable(inventory.get(i).getRateable());
            customObjet.setValueObject(inventory.get(i).getValueObject());

            //Get tous les images
            String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain desc, guidimage";
            Query queryObject2 = entityManager.createQuery(query2);
            queryObject2.setParameter("idObject", inventory.get(i).getIdobject());
            List<ImageobjectEntity> LImages = (List<ImageobjectEntity>) queryObject2.getResultList();

            customObjet.setImage1(LImages.get(0).getGuidimage());
            if(LImages.get(1).getIsmain().length() > 1) {customObjet.setImage2("LogoWorld_Transparence.png");}
            else {customObjet.setImage2(LImages.get(1).getGuidimage());}

            if(LImages.get(2).getIsmain().length() > 1){customObjet.setImage3("LogoWorld_Transparence.png");}
            else {customObjet.setImage3(LImages.get(2).getGuidimage());}

            if(LImages.get(3).getIsmain().length() > 1) {customObjet.setImage4("LogoWorld_Transparence.png");}
            else {customObjet.setImage4(LImages.get(3).getGuidimage());}

            objets.add(customObjet);
        }
        return objets;
    }
    // retourne une liste de tout les object pour un User ..
    @Override
    public List<ObjectsEntity> getListObjectByUserId(String userId) {
        String query = "select o from ObjectsEntity o where o.iduser = :userID";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("userID", userId);
        List<ObjectsEntity> LObject = (List<ObjectsEntity>) queryObject.getResultList();
        return LObject;
    }

    //GetLesItems d'un user qui sont dans sont inventaire selon le userID(proprio des items) et le ID du trade
    @Override
    public List<CustomObjetImageEntity> getListObjectTradeInventory(int transactionID, String userId) {
        List<CustomObjetImageEntity> objets = new ArrayList<>();
        //Get tous les objets
        String query = "select o from ObjectsEntity o where o.iduser = :userID and o.idobject not in " +
                "(select o from ObjectsEntity o where o.iduser = :userID and o.idobject in " +
                "(select ot.idobject from ObjecttransactionEntity ot where ot.idtransaction = :idTransaction))";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("userID", userId);
        queryObject.setParameter("idTransaction", transactionID);
        List<ObjectsEntity> LObject = (List<ObjectsEntity>) queryObject.getResultList();

        //Ajout des objets + images dans un custom Entity
        for(int i=0; i< LObject.size(); i++){
            CustomObjetImageEntity customObjet = new CustomObjetImageEntity();
            customObjet.setIduser(LObject.get(i).getIduser());
            customObjet.setCreationdate(LObject.get(i).getCreationdate());
            customObjet.setDescObject(LObject.get(i).getDescObject());
            customObjet.setIdobject(LObject.get(i).getIdobject());
            customObjet.setIdsubcategory(LObject.get(i).getIdsubcategory());
            customObjet.setIssignaled(LObject.get(i).getIssignaled());
            customObjet.setNameObject(LObject.get(i).getNameObject());
            customObjet.setQuality(LObject.get(i).getQuality());
            customObjet.setRateable(LObject.get(i).getRateable());
            customObjet.setValueObject(LObject.get(i).getValueObject());

            //Get tous les images
            String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain desc, guidimage";
            Query queryObject2 = entityManager.createQuery(query2);
            queryObject2.setParameter("idObject", LObject.get(i).getIdobject());
            List<ImageobjectEntity> LImages = (List<ImageobjectEntity>) queryObject2.getResultList();

            customObjet.setImage1(LImages.get(0).getGuidimage());

            if(LImages.get(1).getIsmain().length() > 1) {customObjet.setImage2("LogoWorld_Transparence.png");}
            else {customObjet.setImage2(LImages.get(1).getGuidimage());}

            if(LImages.get(2).getIsmain().length() > 1){customObjet.setImage3("LogoWorld_Transparence.png");}
            else {customObjet.setImage3(LImages.get(2).getGuidimage());}

            if(LImages.get(3).getIsmain().length() > 1) {customObjet.setImage4("LogoWorld_Transparence.png");}
            else {customObjet.setImage4(LImages.get(3).getGuidimage());}

            objets.add(customObjet);
        }

        return objets;
    }

    //GetLesItems d'un user qui sont en échange selon le userID(proprio des items) et le ID du trade
    @Override
    public List<CustomObjetImageEntity> getTradeObjects(int transactionID, String userID) {
        List<CustomObjetImageEntity> objets = new ArrayList<>();

        String query = "select o from ObjectsEntity o where o.iduser = :idUser and o.idobject in (select ot.idobject from ObjecttransactionEntity ot where ot.idtransaction = :idTransaction)";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("idTransaction", transactionID);
        queryObject.setParameter("idUser", userID);
        List<ObjectsEntity> LObject = (List<ObjectsEntity>) queryObject.getResultList();

        //Ajout des objets + images dans un custom Entity
        for(int i=0; i< LObject.size(); i++){
            CustomObjetImageEntity customObjet = new CustomObjetImageEntity();
            customObjet.setIduser(LObject.get(i).getIduser());
            customObjet.setCreationdate(LObject.get(i).getCreationdate());
            customObjet.setDescObject(LObject.get(i).getDescObject());
            customObjet.setIdobject(LObject.get(i).getIdobject());
            customObjet.setIdsubcategory(LObject.get(i).getIdsubcategory());
            customObjet.setIssignaled(LObject.get(i).getIssignaled());
            customObjet.setNameObject(LObject.get(i).getNameObject());
            customObjet.setQuality(LObject.get(i).getQuality());
            customObjet.setRateable(LObject.get(i).getRateable());
            customObjet.setValueObject(LObject.get(i).getValueObject());

            //Get tous les images
            String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain, guidimage desc";
            Query queryObject2 = entityManager.createQuery(query2);
            queryObject2.setParameter("idObject", LObject.get(i).getIdobject());
            List<ImageobjectEntity> LImages = (List<ImageobjectEntity>) queryObject2.getResultList();

            customObjet.setImage1(LImages.get(0).getGuidimage());
            if(LImages.get(1).getIsmain().length() > 1) {customObjet.setImage2("LogoWorld_Transparence.png");}
            else {customObjet.setImage2(LImages.get(1).getGuidimage());}

            if(LImages.get(2).getIsmain().length() > 1){customObjet.setImage3("LogoWorld_Transparence.png");}
            else {customObjet.setImage3(LImages.get(2).getGuidimage());}

            if(LImages.get(3).getIsmain().length() > 1) {customObjet.setImage4("LogoWorld_Transparence.png");}
            else {customObjet.setImage4(LImages.get(3).getGuidimage());}


            objets.add(customObjet);
        }
        return objets;
    }

    @Override
    public long getNumberOfItem() {
        String query = "select count(o.idobject) from ObjectsEntity o";
        Query queryObject = entityManager.createQuery(query);

        long itemcount = (long)queryObject.getSingleResult();
        return itemcount;
    }

    @Override
    public String findUsersEntityByIdObjects(int IdObject) {

        String query = "select c.descObject from ObjectsEntity c where idobject=:obj";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("obj", IdObject);


        String nameObject = (String)queryObject.getSingleResult();

        return nameObject;
    }
}