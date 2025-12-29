package com.ilnur.bookich.validators;

import com.ilnur.bookich.annotations.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        // this is common practice for string to be null return true
        if(password == null)
            return true;

        boolean hasUpperCaseCharacters = !password.equals(password.toLowerCase());
        boolean hasLowerCaseCharacters = !password.equals(password.toUpperCase());
        boolean hasNumbers = password.matches(".*[0-9].*");
        boolean hasSpecialSymbol = password.matches(".*[^a-zA-Z0-9].*");
        boolean isLongEnough = password.length() >= 8;

        // return true if password is valid, false if not
        return hasUpperCaseCharacters && hasLowerCaseCharacters && hasNumbers && hasSpecialSymbol && isLongEnough;
    }

    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
