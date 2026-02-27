package com.desafio.cadastro.services;

import com.desafio.cadastro.mappers.AddressMapper;
import com.desafio.cadastro.models.Address;
import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.models.dtos.address.AddressResponse;
import com.desafio.cadastro.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressResponse create(AddressCreate dto) {
        Address newAddress = new Address();
        addressMapper.createToEntity(dto, newAddress);
        Address savedAddress = addressRepository.save(newAddress);
        return addressMapper.entityToResponse(savedAddress);
    }
}
