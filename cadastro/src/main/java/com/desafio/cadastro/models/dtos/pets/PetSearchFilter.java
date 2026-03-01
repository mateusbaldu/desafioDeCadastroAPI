package com.desafio.cadastro.models.dtos.pets;

import com.desafio.cadastro.enums.PetSex;
import com.desafio.cadastro.enums.PetType;
import jakarta.validation.constraints.NotNull;

public record PetSearchFilter(
        @NotNull PetType petType,
        String name,
        PetSex sex,
        Float age,
        Float weight,
        String race,
        String city,
        String street
) {
}
