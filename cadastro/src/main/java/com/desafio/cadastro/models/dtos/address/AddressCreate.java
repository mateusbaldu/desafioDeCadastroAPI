package com.desafio.cadastro.models.dtos.address;

public record AddressCreate (
        String street,
        int number,
        String complement,
        String district
){
}
