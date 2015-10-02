package com.tilf.troke.controller;

import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.repository.CustomUserRepository;
import com.tilf.troke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Emmanuel on 2015-09-20.
 */
@Controller
@RequestMapping("/site/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUserContext authContext;

    @Autowired
    private CustomUserRepository customUserRepository;

    @RequestMapping("/get")
    public String getUser(@RequestParam("iduser") String idUser, Model model) {
        UsersEntity user = customUserRepository.findUserById(idUser);
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping("/listusers")
    public String getAllUsers(Model model) {
        List<UsersEntity> usersList = customUserRepository.getAllUsers();
        model.addAttribute("userList", usersList);
        return "site/listusers";
    }
}