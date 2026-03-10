package com.desafio.cadastro.services;

import com.desafio.cadastro.exceptions.ResourceNotFoundException;
import com.desafio.cadastro.mappers.AddressMapper;
import com.desafio.cadastro.models.Address;
import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.models.dtos.address.AddressResponse;
import com.desafio.cadastro.models.dtos.address.AddressUpdate;
import com.desafio.cadastro.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private AddressResponse addressResponse;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .id(1)
                .city("São Paulo")
                .street("Rua das Flores")
                .number("123")
                .complement("Apto 1")
                .district("Centro")
                .build();

        addressResponse = new AddressResponse(1, null, "São Paulo", "Rua das Flores", "123", "Apto 1", "Centro");
    }

    @Nested
    @DisplayName("create()")
    class Create {

        @Test
        @DisplayName("Should create address and return AddressResponse")
        void shouldCreateAddressSuccessfully() {
            AddressCreate dto = new AddressCreate(null, "São Paulo", "Rua das Flores", "123", "Apto 1", "Centro");

            when(addressMapper.createToEntity(dto)).thenReturn(address);
            when(addressRepository.save(address)).thenReturn(address);
            when(addressMapper.entityToResponse(address)).thenReturn(addressResponse);

            AddressResponse result = addressService.create(dto);

            assertNotNull(result);
            assertEquals(addressResponse.id(), result.id());
            assertEquals("São Paulo", result.city());
            verify(addressRepository).save(address);
        }
    }

    @Nested
    @DisplayName("fetchResponseById()")
    class FetchResponseById {

        @Test
        @DisplayName("Should return AddressResponse when ID exists")
        void shouldReturnResponseWhenIdExists() {
            when(addressRepository.findById(1)).thenReturn(Optional.of(address));
            when(addressMapper.entityToResponse(address)).thenReturn(addressResponse);

            AddressResponse result = addressService.fetchResponseById(1);

            assertNotNull(result);
            assertEquals(1, result.id());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when ID does not exist")
        void shouldThrowWhenIdDoesNotExist() {
            when(addressRepository.findById(999)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> addressService.fetchResponseById(999));
        }
    }

    @Nested
    @DisplayName("deleteById()")
    class DeleteById {

        @Test
        @DisplayName("Should delete address when ID exists")
        void shouldDeleteWhenIdExists() {
            when(addressRepository.findById(1)).thenReturn(Optional.of(address));

            addressService.deleteById(1);

            verify(addressRepository).delete(address);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when ID does not exist")
        void shouldThrowWhenIdDoesNotExist() {
            when(addressRepository.findById(999)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> addressService.deleteById(999));
            verify(addressRepository, never()).delete(any());
        }
    }

    @Nested
    @DisplayName("fetchByPetId()")
    class FetchByPetId {

        @Test
        @DisplayName("Should return AddressResponse by pet ID")
        void shouldReturnResponseByPetId() {
            when(addressRepository.findByPet_Id(10)).thenReturn(address);
            when(addressMapper.entityToResponse(address)).thenReturn(addressResponse);

            AddressResponse result = addressService.fetchByPetId(10);

            assertNotNull(result);
            assertEquals(1, result.id());
        }
    }

    @Nested
    @DisplayName("update()")
    class Update {

        @Test
        @DisplayName("Should update address and return AddressResponse")
        void shouldUpdateAddressSuccessfully() {
            AddressUpdate dto = new AddressUpdate(1, null, "Rio de Janeiro", "Av Brasil", "456", null, "Copacabana");
            AddressResponse updatedResponse = new AddressResponse(1, null, "Rio de Janeiro", "Av Brasil", "456", null, "Copacabana");

            when(addressRepository.findById(1)).thenReturn(Optional.of(address));
            when(addressRepository.save(address)).thenReturn(address);
            when(addressMapper.entityToResponse(address)).thenReturn(updatedResponse);

            AddressResponse result = addressService.update(dto);

            assertNotNull(result);
            assertEquals("Rio de Janeiro", result.city());
            verify(addressMapper).updateToEntity(dto, address);
            verify(addressRepository).save(address);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when ID does not exist")
        void shouldThrowWhenIdDoesNotExist() {
            AddressUpdate dto = new AddressUpdate(999, null, "Rio", "Rua X", "1", null, "Bairro");
            when(addressRepository.findById(999)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> addressService.update(dto));
            verify(addressRepository, never()).save(any());
        }
    }
}
