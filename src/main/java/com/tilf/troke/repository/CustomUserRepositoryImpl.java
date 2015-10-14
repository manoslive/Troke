package com.tilf.troke.repository;

import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by jp on 2015-09-21.
 */
@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    // Requêtes sur les Users
    @Override
    public UsersEntity findUserByIdAndPass(String iduser, String pass) {
        String query = "select * from users where iduser = :iduser and pass = :pass";
        Query queryObject = entityManager.createNativeQuery(query, UsersEntity.class);
        queryObject.setParameter("iduser", iduser);
        queryObject.setParameter("pass", pass);

        UsersEntity usersEntity = (UsersEntity) queryObject.getSingleResult();

        return usersEntity;
    }

    @Override
    public UsersEntity findUserById(String iduser) {
        String query = "select * from users where iduser = :iduser";
        Query queryObject = entityManager.createNativeQuery(query, UsersEntity.class);
        queryObject.setParameter("iduser", iduser);
        UsersEntity usersEntity = (UsersEntity) queryObject.getSingleResult();

        return usersEntity;
    }

    @Override
    public List<UsersEntity> getAllUsers() {
        String query = "select * from users";
        Query queryObject = entityManager.createNativeQuery(query, UsersEntity.class);

        List<UsersEntity> usersEntityList = queryObject.getResultList();

        return usersEntityList;
    }

    @Override
    public BigInteger checkUserExistance(String iduser){
        String query = "select count(1) from users where iduser = :iduser";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("iduser", iduser);
        BigInteger count = (BigInteger)queryObject.getSingleResult();

        return count;
    }


    // Requêtes sur les catégories
    @Override
    public List<String> getAllCategories()
    {
        String query = "select NAME_CATEGORY from category";
        Query queryObject = entityManager.createNativeQuery(query);
        List<String> categoryList = (List<String>)queryObject.getResultList();

        return categoryList;
    }

    @Override
    public List<String> getAllSubCategories(String categoryName)
    {
        int number = 11;
        //BigInteger bi = BigInteger.valueOf(number);

        String query = "select NAME_SUBCATEGORY from subcategory where IDCATEGORY=:number1";
        Query queryObject = entityManager.createNativeQuery(query);
        number = getIdCategoryFromCategoryName(categoryName);
        queryObject.setParameter("number1", number);

        List<String> subcategoryList = (List<String>)queryObject.getResultList();

        return subcategoryList;
    }

    // Requêtes sur les objects
    @Override
    public List<ObjectsEntity> getRecentItems()
    {
        TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity as o order by o.creationdate desc", ObjectsEntity.class);
        List<ObjectsEntity> objects = query.setMaxResults(36).getResultList();

        return objects;
    }

    @Override
    public List<ObjectsEntity> getObjectsByCategory(String categoryName)
    {
        TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity as o where exists (select s from SubcategoryEntity s where o.idsubcategory=s.idSubcategory and s.idcategory=:idcategory)", ObjectsEntity.class);
        query.setParameter("idcategory", getIdCategoryFromCategoryName(categoryName));
        List<ObjectsEntity> result = query.getResultList();

        return result;
    }

    @Override
    public List<ObjectsEntity> getObjectsBySubCategory(String subCategoryName)
    {
        TypedQuery<ObjectsEntity> query = entityManager.createQuery("select o from ObjectsEntity o where o.idsubcategory=:idsubcategory", ObjectsEntity.class);
        query.setParameter("idsubcategory", getSubCategoryIdBySubCategoryName(subCategoryName));
        List<ObjectsEntity> result = query.getResultList();

        return result;
    }

    public int getSubCategoryIdBySubCategoryName(String subCategoryName)
    {
        String query = "select IDSUBCATEGORY from subcategory s inner join category c on s.idcategory=c.idcategory where NAME_SUBCATEGORY=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", subCategoryName);
        int idsubcategory = (int)queryObject.getSingleResult();

        return idsubcategory;
    }
    public int getIdCategoryFromCategoryName(String categoryName)
    {
        String query = "select distinct c.idcategory from category c inner join subcategory s on c.idcategory=s.idcategory where name_category=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", categoryName);
        int idcategory = (int) queryObject.getSingleResult();
        return idcategory;
    }
}
