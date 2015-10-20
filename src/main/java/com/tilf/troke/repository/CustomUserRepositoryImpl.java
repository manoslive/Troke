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
}
