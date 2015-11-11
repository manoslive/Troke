package com.tilf.troke.validator;

import com.tilf.troke.domain.UserSignupForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Manu on 2015-10-07.
 */
@Component("userValidator")
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserSignupForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "iduser", "error.iduser", "Le nom d'utilisateur est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "error.lastname", "Le prénom est requis");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "error.firstname", "Le nom de famille est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pass", "error.pass", "Le mot de passe est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pass_confirm", "error.pass_confirm", "La confirmation du mot de passe est requise.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email", "Le courriel est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email_confirm", "error.email_confirm", "La confirmation du courriel est requise.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "error.telephone", "Le numéro de téléphone est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", "error.zipcode", "Le code postal est requis.");
    }
}