package de.hsrm.mi.web.projekt.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface GutesPasswort{
    String message() default "Kein gueltiges Passwort";
    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default {};
}
