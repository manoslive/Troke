package com.tilf.troke.repository;

import com.tilf.troke.entity.ObjectsEntity;

import java.util.List;

/**
 * Created by Manu on 2015-10-19.
 */
public interface CustomObjectRepository {
    // Requêtes sur les catégories
    List<String> getAllCategories();
    List<String> getAllSubCategories(String categoryName);

    // Requêtes sur les objets
    List<ObjectsEntity> getRecentItems();

    List<ObjectsEntity> getObjectsByCategory(String categoryName);
    List<ObjectsEntity> getObjectsBySubCategory(String subCategoryName);
    List<ObjectsEntity> getListObjectByUserId(String userId);
    ObjectsEntity getObjectEntityByIdObject(int id_object);

    // Recherches
    List<ObjectsEntity> getObjectListByKeyword(String keyword);

    //GetItemNameByID
    String getObjectNameByItemID(int itemID);

    //GetLesItems d'un inventaire selon le UserId
    List<ObjectsEntity> getObjectsByUserID(int currentItemID, String userID);

    //GetLesItems d'un user qui sont en échange selon le userID(proprio des items) et le ID du trade
    List<ObjectsEntity> getTradeObjects(int transactionID, String userID);

    //Get les items de l'inventaire d'un user de la page trade (sans les items en échanges)
    List<ObjectsEntity> getListObjectTradeInventory(int transactionID,String userId);
}