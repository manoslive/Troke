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
        number = GetIdCategoryFromCategoryName(categoryName);
        queryObject.setParameter("number1", number);

        List<String> subcategoryList = (List<String>)queryObject.getResultList();

        return subcategoryList;
    }

    public int GetIdCategoryFromCategoryName(String categoryName)
    {
        int idcategory = -1;
        try {
            String query = "select distinct c.idcategory from troke.category c " +
                    "inner join troke.subcategory s on c.idcategory=s.idcategory " +
                    "where name_category=:name";

            Query queryObject = entityManager.createNativeQuery(query);
            queryObject.setParameter("name", categoryName);
            idcategory = (int) queryObject.getSingleResult();
        }
        catch(NoResultException iae)
        {
            System.out.println(iae.fillInStackTrace());
            System.out.println(iae.getMessage());
        }
        return idcategory;
    }

    // Requêtes sur les objects
    @Override
    public List<String> getLast10Objects()
    {
        String query = "select NAME_OBJECT from objects order by creationdate desc limit 10";
        Query queryObject = entityManager.createNativeQuery(query);
        List<String> objects = (List<String>)queryObject.getResultList();

        return objects;
    }

    @Override
    public List<ObjectsEntity> getObjectsByCategory(String categoryName)
    {
        String query = "select * from objects where idcategory=:idcategory";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("idcategory", GetIdCategoryFromCategoryName(categoryName));
        List<ObjectsEntity> objects = queryObject.getResultList();

        return objects;
    }

    @Override
    public List<ObjectsEntity> getObjectsBySubCategory(String subCategoryName)
    {
        String query = "select * from objects where idsubcategory=:idcategory";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("idcategory", getSubCategoryIdBySubCategoryName(subCategoryName));
        List<ObjectsEntity> objects = queryObject.getResultList();

        return objects;
    }

    public int getSubCategoryIdBySubCategoryName(String subCategoryName)
    {
        String query = "select IDSUBCATEGORY from troke.subcategory s inner join troke.category c on s.idcategory=c.idcategory where NAME_SUBCATEGORY=:name";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("name", subCategoryName);
        int idsubcategory = (int)queryObject.getSingleResult();

        return idsubcategory;
    }
}
