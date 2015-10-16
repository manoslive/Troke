package com.tilf.troke.repository;

import com.tilf.troke.entity.CategoryEntity;
import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jp on 2015-09-21.
 */
public interface CustomUserRepository {
    // Requête sur les utilisateurs
    UsersEntity findUserByIdAndPass(String iduser, String pass);
    UsersEntity findUserById(String pass);
    BigInteger checkUserExistance(String iduser);
    List<UsersEntity> getAllUsers();
    // int addUsersEntity(String iduser, String firstname, String lastname, String pass, String email, String telephone, String zipcode);

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
}
