package com.tilf.troke.validator;

import com.tilf.troke.entity.UsersEntity;
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
        return UsersEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "iduser", "error.iduser", "Le nom d'utilisateur est requis.");
    }
}