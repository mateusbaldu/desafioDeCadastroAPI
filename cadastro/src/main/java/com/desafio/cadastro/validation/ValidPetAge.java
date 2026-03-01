package com.desafio.cadastro.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.RECORD_COMPONENT)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PetAgeValidator.class)
public @interface ValidPetAge {
    String message() default "Age must be a valid number and cannot exceed 20 years";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
