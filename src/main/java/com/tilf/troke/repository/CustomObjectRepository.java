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
    ObjectsEntity getObjectEntityByIdObject(int id_object);

    // Recherches
    List<ObjectsEntity> getObjectListByKeyword(String keyword);

    //GetItemNameByID
    String getObjectNameByItemID(int itemID);
    //GetLesItems d'un inventaire selon le UserId
    List<ObjectsEntity> getObjectsByUserID(int currentItemID, String userID);
}
