package com.tilf.troke.repository;

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

    @Override
    public BigInteger checkEmailExistance(String email){
        String query = "select count(1) from users where email = :email";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("email", email);
        return (BigInteger)queryObject.getSingleResult();
    }

    // StartTrade
    @Override
    public UsersEntity getUserFromItem(int itemID)
    {
        String query = "select u from UsersEntity u where u.iduser = (select o.iduser from ObjectsEntity o where o.idobject =:itemID)";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("itemID",itemID);
        UsersEntity user = (UsersEntity) queryObject.getSingleResult();
        return user;
    }

    // Vérification de l'existance de la photo dans la BD
    @Override
    public BigInteger checkAvatarName(String avatarName){
        String query = "select count(1) from users where avatar = :avatarName";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("avatarName", avatarName);
        return (BigInteger)queryObject.getSingleResult();
    }

    // Get Opponent User d'un transaction
    //Recoit le ID de transaction et le current user pour déterminer le user Opposé
    @Override
    public String findOpponentUserID(int transactionID, String currentUserID){
        String query1 = "select t.iduser1 from TransactionsEntity t where t.idtransaction = :idTransaction";
        String query2 = "select t.iduser2 from TransactionsEntity t where t.idtransaction = :idTransaction";
        Query queryObject1 = entityManager.createQuery(query1);
        Query queryObject2 = entityManager.createQuery(query2);
        queryObject1.setParameter("idTransaction", transactionID);
        queryObject2.setParameter("idTransaction", transactionID);
        String userID1 = (String)queryObject1.getSingleResult();
        String userID2 = (String)queryObject2.getSingleResult();

        if(currentUserID.equals(userID1))
            return userID1;
        else
            return userID2;
    }
}
