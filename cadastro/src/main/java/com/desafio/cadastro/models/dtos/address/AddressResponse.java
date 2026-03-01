package com.desafio.cadastro.models.dtos.address;

public record AddressResponse (
        Integer id,
        Integer petId,
        String city,
        String street,
        String number,
        String complement,
        String district
){
}