package com.desafio.cadastro.models.dtos.address;

public record AddressResponse (
        Integer id,
        Integer petId,
        String street,
        int number,
        String complement,
        String district
){
}