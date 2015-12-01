package com.tilf.troke.domain;


import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Alex on 2015-11-26.
 */
public class UserProfil {

    // attribut ...
    @NotNull(message = " *Le prénom ne doit pas être nul.")
    @Size(min=1, max= 35, message = " *Veuillez entrer un prenom entre 1 et 35 lettres ..")
    private String firstname;

    @NotNull(message = " *Le nom de famille ne doit pas être nul.")
    @Size(min=1, max = 35, message=" *Veuillez entrer un nom de famille .. entre 1 et 35 lettres .")
    private String lastname;

    @Email(message = " *Le courriel doit être sous la forme : a@b.c")
    @NotNull(message = " *Le courriel ne doit pas être nul.")
    @Size(min=1, max=100, message=" *Le courriel ne doit pas être nul.")
    private String email;

    @Size(max = 7, min = 7, message = " *Le code postal doit être composé de 3 chiffres et 3 lettres.")
    @NotNull(message = " *Le code postal ne doit pas être nul.")
    private String zipcode;

    @Size(max = 14, min = 14, message = " *Le téléphone doit avoir 10 chiffres.")
    @NotNull(message = " *Le numéro de téléphone ne doit pas être nul.")
    private String telephone;


    private String avatar;



    // getter setter ..
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAvatar() {return avatar;}

    public void setAvatar(String avatar) {this.avatar = avatar;}






}
