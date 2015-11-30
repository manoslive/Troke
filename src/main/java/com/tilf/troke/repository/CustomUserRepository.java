package com.tilf.troke.repository;

import com.tilf.troke.entity.UsersEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jp on 2015-09-21.
 */
public interface CustomUserRepository{
    // Requête sur les utilisateurs
    UsersEntity findUserByIdAndPass(String iduser, String pass);
    UsersEntity findUserById(String pass);
    BigInteger checkEmailExistance(String email);
    BigInteger checkUserExistance(String iduser);
    void updateUserPassword(UsersEntity user);
    List<UsersEntity> getAllUsers();
    // int addUsersEntity(String iduser, String firstname, String lastname, String pass, String email, String telephone, String zipcode);

    //StartTrade
    UsersEntity getUserFromItem(int itemID);

    // Vérification du nom d'avatar dans la BD
    BigInteger checkAvatarName(String avatarName);

    // Get Opponent User d'un transaction
    //Recoit le ID de transaction et le current user pour déterminer le user Opposé
    UsersEntity findOpponentUser(int transactionID, String currentUserID);

}
