package com.ilnur.bookich.annotations;

import com.ilnur.bookich.validators.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/*
This is "bazoviy minimum" for custom validator annotation, you should define all of those 3 methods inside
 */

@Documented
// here custom validator is specified
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Password should contain numbers and special symbols (@, $, etc.)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
