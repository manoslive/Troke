package com.tilf.troke.repository;

import com.tilf.troke.entity.ObjectsEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
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
    public List<ObjectsEntity> getObjectsByCategory(Set<String> categoryName) {
        // TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity as o where exists (select s.idSubcategory from SubcategoryEntity s where o.idsubcategory=s.idSubcategory and s.idcategory in :idlist)", ObjectsEntity.class);
        Query query = entityManager.createNativeQuery("select IDOBJECT, NAME_OBJECT, DESC_OBJECT, guid, s.idSUBCATEGORY, VALUE_OBJECT, QUALITY, IDUSER, RATEABLE, ISSIGNALED, CREATIONDATE from objects o inner join subcategory s on s.IDSUBCATEGORY = o.IDSUBCATEGORY inner join category c on c.IDCATEGORY = s.IDCATEGORY where c.IDCATEGORY in :idlist", ObjectsEntity.class);
        // query.setParameter("idcategory", getIdCategoryFromCategoryName("à changer"));
        query.setParameter("idlist", getCatIdListFromCatNameSet(categoryName));
        List<ObjectsEntity> result = query.getResultList();

        return result;
    }

    @Override
    public List<ObjectsEntity> getObjectsBySubCategory(String subCategoryName) {
        TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity o where o.idsubcategory=:idsubcategory", ObjectsEntity.class);
        query.setParameter("idsubcategory", getSubCategoryIdBySubCategoryName(subCategoryName));
        List<ObjectsEntity> result = query.getResultList();

        return result;
    }

    public int getSubCategoryIdBySubCategoryName(String subCategoryName) {
        String query = "select IDSUBCATEGORY from subcategory s inner join category c on s.idcategory=c.idcategory where NAME_SUBCATEGORY=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", subCategoryName);
        int idsubcategory = (int) queryObject.getSingleResult();

        return idsubcategory;
    }

    public int getIdCategoryFromCategoryName(String categoryName) {
        String query = "select distinct c.idcategory from category c inner join subcategory s on c.idcategory=s.idcategory where name_category=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", categoryName);
        int idcategory = (int) queryObject.getSingleResult();
        return idcategory;
    }

    @Override
    public ObjectsEntity getObjectEntityByIdObject(int id_object) {
        String query = "select o from ObjectsEntity o where o.idobject=:idobject";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("idobject", id_object);
        ObjectsEntity obj = (ObjectsEntity) queryObject.getSingleResult();

        return obj;
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
    public List<ObjectsEntity> getObjectsByUserID(int currentItemID, String userID) {
        String query = "select o from ObjectsEntity o where o.iduser = :userID and o.idobject != :currentItemID";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("currentItemID", currentItemID);
        queryObject.setParameter("userID", userID);
        List<ObjectsEntity> inventory = (List<ObjectsEntity>) queryObject.getResultList();
        return inventory;
    }

    @Override
    public List<ObjectsEntity> getListObjectByUserId(String userId) {
        String query = "select o from ObjectsEntity o where o.iduser = :userID";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("userID", userId);
        List<ObjectsEntity> LObject = (List<ObjectsEntity>) queryObject.getResultList();
        return LObject;
    }

    @Override
    public List<ObjectsEntity> getListObjectTradeInventory(int transactionID, String userId) {
        String query = "select o from ObjectsEntity o where o.iduser = :userID and o.idobject not in " +
                "(select o from ObjectsEntity o where o.iduser = :userID and o.idobject in " +
                "(select ot.idobject from ObjecttransactionEntity ot where ot.idtransaction = :idTransaction))";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("userID", userId);
        queryObject.setParameter("idTransaction", transactionID);
        List<ObjectsEntity> LObject = (List<ObjectsEntity>) queryObject.getResultList();
        return LObject;
    }

    //GetLesItems d'un user qui sont en échange selon le userID(proprio des items) et le ID du trade
    @Override
    public List<ObjectsEntity> getTradeObjects(int transactionID, String userID) {
        String query = "select o from ObjectsEntity o where o.iduser = :idUser and o.idobject in (select ot.idobject from ObjecttransactionEntity ot where ot.idtransaction = :idTransaction)";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("idTransaction", transactionID);
        queryObject.setParameter("idUser", userID);
        List<ObjectsEntity> LObject = (List<ObjectsEntity>) queryObject.getResultList();
        return LObject;
    }
}