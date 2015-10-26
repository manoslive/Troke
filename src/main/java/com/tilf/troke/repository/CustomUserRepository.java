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
    List<UsersEntity> getAllUsers();
    // int addUsersEntity(String iduser, String firstname, String lastname, String pass, String email, String telephone, String zipcode);

    //StartTrade
    UsersEntity getUserFromItem(int itemID);

    // Vérification du nom d'avatar dans la BD
    BigInteger checkAvatarName(String avatarName);
}
