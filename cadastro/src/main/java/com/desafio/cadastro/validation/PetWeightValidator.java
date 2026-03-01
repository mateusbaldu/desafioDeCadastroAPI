package com.desafio.cadastro.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PetWeightValidator implements ConstraintValidator<ValidPetWeight, String> {

    private static final String NUMERIC_PATTERN = "\\d+([.,]\\d+)?";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        if (!value.matches(NUMERIC_PATTERN)) return false;
        float weight = Float.parseFloat(value.replace(",", "."));
        return weight >= 0.5f && weight <= 60f;
    }
}
