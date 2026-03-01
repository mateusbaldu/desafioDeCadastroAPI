package com.desafio.cadastro.models.dtos.address;

public record AddressCreate (
        Integer petId,
        String city,
        String street,
        String number,
        String complement,
        String district
){
}
