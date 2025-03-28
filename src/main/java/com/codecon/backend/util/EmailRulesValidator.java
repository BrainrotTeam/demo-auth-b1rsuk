package com.codecon.backend.util;

import com.codecon.backend.util.exception.InvalidEmailException;
import org.apache.commons.validator.routines.EmailValidator;

public class EmailRulesValidator {

    private EmailRulesValidator() {}

    public static void validateEmail(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        emailValidator.isValid(email);
        if (!emailValidator.isValid(email)) {
            throw new InvalidEmailException(email);
        }
    }

}
