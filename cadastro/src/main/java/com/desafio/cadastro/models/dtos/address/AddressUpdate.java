package com.desafio.cadastro.models.dtos.address;

public record AddressUpdate (
        Integer petId,
        String street,
        int number,
        String complement,
        String district
){
}
