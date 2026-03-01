package com.desafio.cadastro.models.dtos.address;

public record AddressUpdate (
        Integer id,
        Integer petId,
        String city,
        String street,
        String number,
        String complement,
        String district
){
}
