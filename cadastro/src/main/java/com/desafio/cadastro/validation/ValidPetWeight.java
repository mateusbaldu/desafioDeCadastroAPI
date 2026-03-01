package com.desafio.cadastro.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.RECORD_COMPONENT)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PetWeightValidator.class)
public @interface ValidPetWeight {
    String message() default "Weight must be a valid number between 0.5kg and 60kg";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
