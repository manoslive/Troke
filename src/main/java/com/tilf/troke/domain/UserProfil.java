package com.tilf.troke.domain;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Alex on 2015-11-26.
 */
public class UserProfil {

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @NotNull(message = "Le nom de famille ne doit pas être nul.")
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @NotNull(message = "Le prénom ne doit pas être nul.")
    private String firstname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Email(message = "Le courriel doit être sous la forme : a@b.c")
    @NotNull(message = "Le courriel ne doit pas être nul.")
    private String email;

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Size(max = 7, min = 7, message = "Le code postal doit être composé de 3 chiffres et 3 lettres.")
    @NotNull(message = "Le code postal ne doit pas être nul.")
    private String zipcode;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Size(max = 14, min = 14, message = "Le téléphone doit avoir 10 chiffres.")
    @NotNull(message = "Le numéro de téléphone ne doit pas être nul.")
    private String telephone;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String avatar;
}
