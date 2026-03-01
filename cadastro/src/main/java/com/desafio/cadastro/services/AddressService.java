package com.desafio.cadastro.services;

import com.desafio.cadastro.exceptions.ResourceNotFoundException;
import com.desafio.cadastro.mappers.AddressMapper;
import com.desafio.cadastro.models.Address;
import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.models.dtos.address.AddressResponse;
import com.desafio.cadastro.models.dtos.address.AddressUpdate;
import com.desafio.cadastro.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public AddressResponse create(AddressCreate dto) {
        Address newAddress = addressMapper.createToEntity(dto);
        Address savedAddress = addressRepository.save(newAddress);
        return addressMapper.entityToResponse(savedAddress);
    }

    public AddressResponse fetchResponseById(int id) {
        Address addressFound = fetchById(id);
        return addressMapper.entityToResponse(addressFound);
    }

    @Transactional
    public void deleteById(int id) {
        Address addressFound = fetchById(id);
        addressRepository.delete(addressFound);
    }

    @Transactional(readOnly = true)
    public AddressResponse fetchByPetId(int id) {
        Address addressFound = addressRepository.findByPet_Id(id);
        return addressMapper.entityToResponse(addressFound);
    }

    @Transactional
    public AddressResponse update(AddressUpdate dto) {
        Address addressFound = fetchById(dto.id());
        addressMapper.updateToEntity(dto, addressFound);
        addressRepository.save(addressFound);
        return addressMapper.entityToResponse(addressFound);
    }

    private Address fetchById(int id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }
}
