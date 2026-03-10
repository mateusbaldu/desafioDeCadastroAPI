package com.desafio.cadastro.services;

import com.desafio.cadastro.enums.PetSex;
import com.desafio.cadastro.enums.PetType;
import com.desafio.cadastro.exceptions.InvalidArgumentException;
import com.desafio.cadastro.exceptions.ResourceNotFoundException;
import com.desafio.cadastro.mappers.AddressMapper;
import com.desafio.cadastro.mappers.PetMapper;
import com.desafio.cadastro.models.Address;
import com.desafio.cadastro.models.Pets;
import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.models.dtos.pets.PetCreate;
import com.desafio.cadastro.models.dtos.pets.PetResponse;
import com.desafio.cadastro.models.dtos.pets.PetSearchFilter;
import com.desafio.cadastro.models.dtos.pets.PetUpdate;
import com.desafio.cadastro.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetMapper petMapper;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private PetService petService;

    private Pets pet;
    private PetResponse petResponse;

    @BeforeEach
    void setUp() {
        pet = Pets.builder()
                .id(1)
                .name("Rex Silva")
                .petType(PetType.DOG)
                .petSex(PetSex.MALE)
                .age(3.0f)
                .weight(10.5f)
                .race("Labrador")
                .build();

        petResponse = new PetResponse(1, "Rex Silva", PetType.DOG, PetSex.MALE, 3.0f, 10.5f, "Labrador", null);
    }

    @Nested
    @DisplayName("search()")
    class Search {

        @Test
        @DisplayName("Should return filtered pet list")
        @SuppressWarnings("unchecked")
        void shouldReturnFilteredPets() {
            PetSearchFilter filter = new PetSearchFilter(PetType.DOG, null, null, null, null, null, null, null);

            when(petRepository.findAll(any(Specification.class))).thenReturn(List.of(pet));
            when(petMapper.entityToResponse(pet)).thenReturn(petResponse);

            List<PetResponse> result = petService.search(filter);

            assertEquals(1, result.size());
            assertEquals("Rex Silva", result.get(0).name());
        }

        @Test
        @DisplayName("Should return empty list when no pets found")
        @SuppressWarnings("unchecked")
        void shouldReturnEmptyList() {
            PetSearchFilter filter = new PetSearchFilter(PetType.CAT, null, null, null, null, null, null, null);

            when(petRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<PetResponse> result = petService.search(filter);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("create()")
    class Create {

        @Test
        @DisplayName("Should create pet without address successfully")
        void shouldCreatePetWithoutAddress() {
            PetCreate dto = new PetCreate("Rex Silva", PetType.DOG, PetSex.MALE, "3", "10.5", "Labrador", null);

            when(petRepository.save(any(Pets.class))).thenReturn(pet);
            when(petMapper.entityToResponse(pet)).thenReturn(petResponse);

            PetResponse result = petService.create(dto);

            assertNotNull(result);
            assertEquals("Rex Silva", result.name());
            verify(petRepository).save(any(Pets.class));
        }

        @Test
        @DisplayName("Should create pet with address")
        void shouldCreatePetWithAddress() {
            AddressCreate addressCreate = new AddressCreate(null, "São Paulo", "Rua X", "100", null, "Centro");
            PetCreate dto = new PetCreate("Rex Silva", PetType.DOG, PetSex.MALE, "3", "10.5", "Labrador", addressCreate);
            Address address = Address.builder().city("São Paulo").street("Rua X").number("100").district("Centro").build();

            when(addressMapper.createToEntity(addressCreate)).thenReturn(address);
            when(petRepository.save(any(Pets.class))).thenReturn(pet);
            when(petMapper.entityToResponse(pet)).thenReturn(petResponse);

            PetResponse result = petService.create(dto);

            assertNotNull(result);
            verify(addressMapper).createToEntity(addressCreate);
        }

        @Test
        @DisplayName("Should use NOT INFORMED when name is null")
        void shouldUseNotInformedWhenNameIsNull() {
            PetCreate dto = new PetCreate(null, PetType.DOG, PetSex.MALE, "3", "10.5", "Labrador", null);
            Pets savedPet = Pets.builder().id(1).name("NOT INFORMED").petType(PetType.DOG).petSex(PetSex.MALE).age(3.0f).weight(10.5f).race("Labrador").build();
            PetResponse response = new PetResponse(1, "NOT INFORMED", PetType.DOG, PetSex.MALE, 3.0f, 10.5f, "Labrador", null);

            when(petRepository.save(any(Pets.class))).thenReturn(savedPet);
            when(petMapper.entityToResponse(savedPet)).thenReturn(response);

            PetResponse result = petService.create(dto);

            assertEquals("NOT INFORMED", result.name());
        }

        @Test
        @DisplayName("Should throw InvalidArgumentException when name has no last name")
        void shouldThrowWhenNameHasNoLastName() {
            PetCreate dto = new PetCreate("Rex", PetType.DOG, PetSex.MALE, "3", "10.5", "Labrador", null);

            assertThrows(InvalidArgumentException.class, () -> petService.create(dto));
        }

        @Test
        @DisplayName("Should throw InvalidArgumentException when name contains numbers")
        void shouldThrowWhenNameContainsNumbers() {
            PetCreate dto = new PetCreate("Rex 123", PetType.DOG, PetSex.MALE, "3", "10.5", "Labrador", null);

            assertThrows(InvalidArgumentException.class, () -> petService.create(dto));
        }

        @Test
        @DisplayName("Should use NOT INFORMED when race is null")
        void shouldUseNotInformedWhenRaceIsNull() {
            PetCreate dto = new PetCreate("Rex Silva", PetType.DOG, PetSex.MALE, "3", "10.5", null, null);

            when(petRepository.save(any(Pets.class))).thenReturn(pet);
            when(petMapper.entityToResponse(pet)).thenReturn(petResponse);

            PetResponse result = petService.create(dto);

            assertNotNull(result);
            verify(petRepository).save(any(Pets.class));
        }

        @Test
        @DisplayName("Should throw InvalidArgumentException when race contains numbers")
        void shouldThrowWhenRaceContainsNumbers() {
            PetCreate dto = new PetCreate("Rex Silva", PetType.DOG, PetSex.MALE, "3", "10.5", "Lab123", null);

            assertThrows(InvalidArgumentException.class, () -> petService.create(dto));
        }

        @Test
        @DisplayName("Should convert comma to dot in parseNumber")
        void shouldParseCommaAsDecimalSeparator() {
            PetCreate dto = new PetCreate("Rex Silva", PetType.DOG, PetSex.MALE, "3,5", "10,5", "Labrador", null);

            when(petRepository.save(any(Pets.class))).thenReturn(pet);
            when(petMapper.entityToResponse(pet)).thenReturn(petResponse);

            PetResponse result = petService.create(dto);

            assertNotNull(result);
            verify(petRepository).save(any(Pets.class));
        }

        @Test
        @DisplayName("Should use 0 when age is null")
        void shouldUseZeroWhenAgeIsNull() {
            PetCreate dto = new PetCreate("Rex Silva", PetType.DOG, PetSex.MALE, null, "10.5", "Labrador", null);

            when(petRepository.save(any(Pets.class))).thenReturn(pet);
            when(petMapper.entityToResponse(pet)).thenReturn(petResponse);

            PetResponse result = petService.create(dto);

            assertNotNull(result);
        }
    }

    @Nested
    @DisplayName("update()")
    class Update {

        @Test
        @DisplayName("Should update existing pet successfully")
        void shouldUpdateExistingPet() {
            PetUpdate dto = new PetUpdate(1, "Rex Atualizado", "4", "12", "Golden", null);
            PetResponse updatedResponse = new PetResponse(1, "Rex Atualizado", PetType.DOG, PetSex.MALE, 4.0f, 12.0f, "Golden", null);

            when(petRepository.findById(1)).thenReturn(Optional.of(pet));
            when(petRepository.save(pet)).thenReturn(pet);
            when(petMapper.entityToResponse(pet)).thenReturn(updatedResponse);

            PetResponse result = petService.update(dto);

            assertNotNull(result);
            assertEquals("Rex Atualizado", result.name());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when pet not found")
        void shouldThrowWhenPetNotFound() {
            PetUpdate dto = new PetUpdate(999, "Rex Silva", "3", "10.5", "Labrador", null);

            when(petRepository.findById(999)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> petService.update(dto));
            verify(petRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should update pet with existing address")
        void shouldUpdatePetWithExistingAddress() {
            Address existingAddress = Address.builder().id(1).city("São Paulo").street("Rua X").number("100").district("Centro").build();
            pet.setAddress(existingAddress);

            AddressCreate addressDto = new AddressCreate(null, "Rio", "Av Brasil", "200", null, "Copacabana");
            PetUpdate dto = new PetUpdate(1, "Rex Atualizado", "4", "12", "Golden", addressDto);

            when(petRepository.findById(1)).thenReturn(Optional.of(pet));
            when(petRepository.save(pet)).thenReturn(pet);
            when(petMapper.entityToResponse(pet)).thenReturn(petResponse);

            PetResponse result = petService.update(dto);

            assertNotNull(result);
            verify(addressMapper).updateFromCreate(eq(addressDto), eq(existingAddress));
        }

        @Test
        @DisplayName("Should update pet adding new address")
        void shouldUpdatePetAddingNewAddress() {
            pet.setAddress(null);

            AddressCreate addressDto = new AddressCreate(null, "Rio", "Av Brasil", "200", null, "Copacabana");
            PetUpdate dto = new PetUpdate(1, "Rex Atualizado", "4", "12", "Golden", addressDto);

            when(petRepository.findById(1)).thenReturn(Optional.of(pet));
            when(petRepository.save(pet)).thenReturn(pet);
            when(petMapper.entityToResponse(pet)).thenReturn(petResponse);

            PetResponse result = petService.update(dto);

            assertNotNull(result);
            verify(addressMapper).updateFromCreate(eq(addressDto), any(Address.class));
        }
    }

    @Nested
    @DisplayName("delete()")
    class Delete {

        @Test
        @DisplayName("Should delete existing pet")
        void shouldDeleteExistingPet() {
            when(petRepository.findById(1)).thenReturn(Optional.of(pet));

            petService.delete(1);

            verify(petRepository).delete(pet);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when pet not found")
        void shouldThrowWhenPetNotFound() {
            when(petRepository.findById(999)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> petService.delete(999));
            verify(petRepository, never()).delete(any(Pets.class));
        }
    }
}
