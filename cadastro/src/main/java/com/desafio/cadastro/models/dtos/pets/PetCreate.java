package com.desafio.cadastro.models.dtos.pets;

import com.desafio.cadastro.enums.PetSex;
import com.desafio.cadastro.enums.PetType;
import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.validation.ValidPetAge;
import com.desafio.cadastro.validation.ValidPetWeight;

public record PetCreate(
        String name,
        PetType petType,
        PetSex petSex,
        @ValidPetAge String age,
        @ValidPetWeight String weight,
        String race,
        AddressCreate address
) {
}
