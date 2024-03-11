package com.mock.validatePassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        boolean hasNumber = password.matches(".*\\d.*");
        
        boolean hasCapitalLetter = password.matches(".*[A-Z].*");
        
        boolean hasMinLength = password.length() >= 6;
        return hasNumber && hasCapitalLetter && hasMinLength;
    }
}