package com.codecon.backend.util;

import com.codecon.backend.model.dto.Authentication;
import com.codecon.backend.util.exception.InvalidPasswordException;
import org.mindrot.jbcrypt.BCrypt;
import org.passay.*;

import java.util.Arrays;

public class PasswordRulesValidator {

    private PasswordRulesValidator() {}

    public static void validatePassword(Authentication authentication, String hashedPassword) {
        String password = authentication.getPassword();
        String email = authentication.getEmail();

        if (!BCrypt.checkpw(password, hashedPassword)) {
            String errorMessage = String.format("Invalid password attempt for user with email '%s'. The provided password does not match the expected hashed password.", email);
            throw new InvalidPasswordException(errorMessage);
        };

    }

    public static void validatePassword(String password) {
        PasswordValidator validator = new PasswordValidator(
                Arrays.asList(
                        new LengthRule(8, 16),
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        new WhitespaceRule()
                )
        );

        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
            String errorMessages = String.join(", ", validator.getMessages(result));
            throw new InvalidPasswordException(errorMessages);
        }
    }

}
