package com.desafio.cadastro.models.dtos.pets;

import com.desafio.cadastro.enums.PetSex;
import com.desafio.cadastro.enums.PetType;
import com.desafio.cadastro.models.dtos.address.AddressResponse;

public record PetResponse(
        Integer id,
        String name,
        PetType petType,
        PetSex petSex,
        float age,
        float weight,
        String race,
        AddressResponse address
) {
}
