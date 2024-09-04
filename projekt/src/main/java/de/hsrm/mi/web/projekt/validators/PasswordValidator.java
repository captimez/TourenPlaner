package de.hsrm.mi.web.projekt.validators; 

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<GutesPasswort, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    
        return !(value.toLowerCase().contains("17") || value.toLowerCase().contains("siebzehn"));
    }
    

}