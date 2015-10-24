package com.tilf.troke.domain;

import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Manu on 2015-10-21.
 */
public class UserSignupForm {
    @NotNull(message = "Le nom d'usager ne doit pas être nul.")
    @Size(min = 4, max = 20, message = "Le nom d'usager doit comporter de 4 à 20 caractères.")
    private String iduser;

    @NotNull(message = "Le nom de famille ne doit pas être nul.")
    private String lastname;

    @NotNull(message = "Le prénom ne doit pas être nul.")
    private String firstname;

    @NotNull(message = "Le mot de passe ne doit pas être nul.")
    private String pass;

    @NotNull(message = "La confirmation du mot de passe de doit pas être nulle.")
    private String pass_confirm;

    @Email(message = "Le courriel doit être sous la forme : a@b.c")
    @NotNull(message = "Le courriel ne doit pas être nul.")
    private String email;

    @NotNull(message = "La confirmation du courriel ne doit pas être nulle.")
    private String email_confirm;

    @Size(max = 14, min = 14, message = "Le téléphone doit avoir 10 chiffres.")
    @NotNull(message = "Le numéro de téléphone ne doit pas être nul.")
    private String telephone;

    @Size(max = 7, min = 7, message = "Le code postal doit être composé de 3 chiffres et 3 lettres.")
    @NotNull(message = "Le code postal ne doit pas être nul.")
    private String zipcode;

    private MultipartFile avatar;


    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass_confirm() {
        return pass_confirm;
    }

    public void setPass_confirm(String pass_confirm) {
        this.pass_confirm = pass_confirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_confirm() {
        return email_confirm;
    }

    public void setEmail_confirm(String email_confirm) {
        this.email_confirm = email_confirm;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    @AssertTrue(message = "Les deux champs doivent être identiques.")
    private boolean isValid() {
        return (this.pass.equals(this.pass_confirm)) && this.email.equals(this.email_confirm);
    }
}
