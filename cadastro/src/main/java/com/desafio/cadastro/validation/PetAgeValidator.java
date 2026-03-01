package com.desafio.cadastro.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PetAgeValidator implements ConstraintValidator<ValidPetAge, String> {

    private static final String NUMERIC_PATTERN = "\\d+([.,]\\d+)?";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        if (!value.matches(NUMERIC_PATTERN)) return false;
        float age = Float.parseFloat(value.replace(",", "."));
        return age <= 20;
    }
}
