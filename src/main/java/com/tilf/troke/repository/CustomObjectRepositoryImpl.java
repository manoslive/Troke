package com.tilf.troke.repository;

import com.tilf.troke.entity.CustomObjetImageEntity;
import com.tilf.troke.entity.ImageobjectEntity;
import com.tilf.troke.entity.ObjectsEntity;
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
    public List<ObjectsEntity> getRecentItems() {
        TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity as o order by o.creationdate desc", ObjectsEntity.class);
        List<ObjectsEntity> objects = query.setMaxResults(36).getResultList();

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
    public List<ObjectsEntity> getObjectsBySubCategory(Set<String> subCategoryName) {
        // TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity as o where exists (select s.idSubcategory from SubcategoryEntity s where o.idsubcategory=s.idSubcategory and s.idcategory in :idlist)", ObjectsEntity.class);
        Query query = entityManager.createNativeQuery("select IDOBJECT, NAME_OBJECT, DESC_OBJECT, guid, idSUBCATEGORY, VALUE_OBJECT, QUALITY, IDUSER, RATEABLE, ISSIGNALED, CREATIONDATE from objects  where IDSUBCATEGORY in :idlist", ObjectsEntity.class);
        // query.setParameter("idcategory", getIdCategoryFromCategoryName("à changer"));
        query.setParameter("idlist", getSubCatIdListFromSubCatNameSet(subCategoryName));
        List<ObjectsEntity> result = query.getResultList();

        return result;
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

        String query = "select NAME_SUBCATEGORY from troke.category c inner join troke.subcategory s on c.idcategory=s.idcategory where c.idcategory=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", categoryname);
        subCatList = (List<String>) queryObject.getResultList();
        return subCatList;
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

        //Get tous les images
        String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain, guidimage desc";
        Query queryObject2 = entityManager.createQuery(query2);
        queryObject2.setParameter("idObject", id_object);
        List<ImageobjectEntity> LImages = (List<ImageobjectEntity>) queryObject2.getResultList();

        customObjet.setImage1(LImages.get(0).getGuidimage());
        customObjet.setImage2(LImages.get(1).getGuidimage());
        customObjet.setImage3(LImages.get(2).getGuidimage());
        customObjet.setImage4(LImages.get(3).getGuidimage());

        return customObjet;
    }

    // Recherches
    @Override
    public List<ObjectsEntity> getObjectListByKeyword(String keyword) {
        String query = "select o from ObjectsEntity o where UPPER(o.nameObject) like UPPER(:keyword)";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("keyword", "%" + keyword + "%");
        List<ObjectsEntity> objList = (List<ObjectsEntity>) queryObject.getResultList();

        return objList;
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
            String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain, guidimage desc";
            Query queryObject2 = entityManager.createQuery(query2);
            queryObject2.setParameter("idObject", inventory.get(i).getIdobject());
            List<ImageobjectEntity> LImages = (List<ImageobjectEntity>) queryObject2.getResultList();

            customObjet.setImage1(LImages.get(0).getGuidimage());
            customObjet.setImage2(LImages.get(1).getGuidimage());
            customObjet.setImage3(LImages.get(2).getGuidimage());
            customObjet.setImage4(LImages.get(3).getGuidimage());

            objets.add(customObjet);
        }
        return objets;
    }

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
            String query2 = "select o from ImageobjectEntity o where o.idobject = :idObject order by ismain, guidimage desc";
            Query queryObject2 = entityManager.createQuery(query2);
            queryObject2.setParameter("idObject", LObject.get(i).getIdobject());
            List<ImageobjectEntity> LImages = (List<ImageobjectEntity>) queryObject2.getResultList();

            customObjet.setImage1(LImages.get(0).getGuidimage());
            customObjet.setImage2(LImages.get(1).getGuidimage());
            customObjet.setImage3(LImages.get(2).getGuidimage());
            customObjet.setImage4(LImages.get(3).getGuidimage());

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
            customObjet.setImage2(LImages.get(1).getGuidimage());
            customObjet.setImage3(LImages.get(2).getGuidimage());
            customObjet.setImage4(LImages.get(3).getGuidimage());

            objets.add(customObjet);
        }
        return objets;
    }
}