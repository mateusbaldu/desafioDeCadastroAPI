package com.desafio.cadastro.models.dtos.pets;

import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.validation.ValidPetAge;
import com.desafio.cadastro.validation.ValidPetWeight;

public record PetUpdate(
        Integer id,
        String name,
        @ValidPetAge String age,
        @ValidPetWeight String weight,
        String race,
        AddressCreate address
) {
}