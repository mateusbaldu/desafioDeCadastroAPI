package com.desafio.cadastro.services;

import com.desafio.cadastro.exceptions.InvalidArgumentException;
import com.desafio.cadastro.exceptions.ResourceNotFoundException;
import com.desafio.cadastro.mappers.AddressMapper;
import com.desafio.cadastro.mappers.PetMapper;
import com.desafio.cadastro.models.Address;
import com.desafio.cadastro.models.Pets;
import com.desafio.cadastro.models.dtos.pets.PetCreate;
import com.desafio.cadastro.models.dtos.pets.PetResponse;
import com.desafio.cadastro.models.dtos.pets.PetUpdate;
import com.desafio.cadastro.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.desafio.cadastro.models.dtos.pets.PetSearchFilter;
import com.desafio.cadastro.repositories.PetSpecification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetService {

    private static final String NOT_INFORMED = "NOT INFORMED";

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final AddressMapper addressMapper;

    @Transactional(readOnly = true)
    public List<PetResponse> search(PetSearchFilter filter) {
        return petRepository.findAll(PetSpecification.build(filter))
                .stream()
                .map(petMapper::entityToResponse)
                .toList();
    }

    @Transactional
    public PetResponse create(PetCreate dto) {
        Pets pet = new Pets();
        pet.setName(normalizeName(dto.name()));
        pet.setPetType(dto.petType());
        pet.setPetSex(dto.petSex());
        pet.setAge(parseNumber(dto.age()));
        pet.setWeight(parseNumber(dto.weight()));
        pet.setRace(normalizeRace(dto.race()));

        Optional.ofNullable(dto.address()).ifPresent(addr -> {
            Address address = addressMapper.createToEntity(addr);
            address.setNumber(normalizeText(addr.number()));
            pet.setAddress(address);
        });

        return petMapper.entityToResponse(petRepository.save(pet));
    }

    @Transactional
    public PetResponse update(PetUpdate dto) {
        Pets pet = petRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        pet.setName(normalizeName(dto.name()));
        pet.setAge(parseNumber(dto.age()));
        pet.setWeight(parseNumber(dto.weight()));
        pet.setRace(normalizeRace(dto.race()));

        Optional.ofNullable(dto.address()).ifPresent(addr -> {
            Address address = pet.getAddress() != null ? pet.getAddress() : new Address();
            addressMapper.updateFromCreate(addr, address);
            address.setNumber(normalizeText(addr.number()));
            pet.setAddress(address);
        });

        return petMapper.entityToResponse(petRepository.save(pet));
    }

    @Transactional
    public void delete(Integer id) {
        Pets pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        petRepository.delete(pet);
    }

    private String normalizeName(String name) {
        if (name == null || name.isBlank()) return NOT_INFORMED;
        if (!name.matches("[A-Za-z ]+") || name.trim().split("\\s+").length < 2)
            throw new InvalidArgumentException("Name must contain only letters and include both first and last name");
        return name.trim();
    }

    private String normalizeRace(String race) {
        if (race == null || race.isBlank()) return NOT_INFORMED;
        if (!race.matches("[A-Za-z ]+"))
            throw new InvalidArgumentException("Race cannot contain numbers or special characters");
        return race.trim();
    }

    private float parseNumber(String value) {
        return (value == null || value.isBlank()) ? 0f : Float.parseFloat(value.replace(",", "."));
    }

    private String normalizeText(String value) {
        return (value == null || value.isBlank()) ? NOT_INFORMED : value.trim();
    }
}
