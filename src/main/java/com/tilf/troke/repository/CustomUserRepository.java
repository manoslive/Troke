package com.tilf.troke.repository;

import com.tilf.troke.entity.CategoryEntity;
import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jp on 2015-09-21.
 */
public interface CustomUserRepository{
    // Requête sur les utilisateurs
    UsersEntity findUserByIdAndPass(String iduser, String pass);
    UsersEntity findUserById(String pass);
    BigInteger checkUserExistance(String iduser);
    List<UsersEntity> getAllUsers();
    // int addUsersEntity(String iduser, String firstname, String lastname, String pass, String email, String telephone, String zipcode);
}
