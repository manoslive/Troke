package com.tilf.troke.repository;

import com.tilf.troke.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Emmanuel on 2015-09-20.
 */
@Repository
public interface UserRepository extends JpaRepository<UsersEntity, String> {

    UsersEntity findUsersEntityByIduserAndPass(String iduser, String pass);

    UsersEntity save(UsersEntity user);

    UsersEntity findUsersEntityByIduser(String i);



    //void addUsersEntity(String iduser, String lastname, String firstname, String pass, String avatar, char isbanned, char isonline, Date creationdate, String email, String telephone, String zipcode, char permissionlevel, char isvip);
}