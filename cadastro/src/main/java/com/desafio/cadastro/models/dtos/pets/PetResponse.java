package com.desafio.cadastro.models.dtos.pets;

import com.desafio.cadastro.enums.PetSex;
import com.desafio.cadastro.enums.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetResponse (
        Integer id,
        String name,
        PetType petType,
        PetSex petSex,
        String age,
        String weight,
        String race
){
}
