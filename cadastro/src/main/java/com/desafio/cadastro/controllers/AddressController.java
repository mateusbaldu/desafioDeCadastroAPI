package com.desafio.cadastro.controllers;

import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.models.dtos.address.AddressResponse;
import com.desafio.cadastro.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> create(@RequestBody AddressCreate dto) {
        return ResponseEntity.ok(addressService.create(dto));
    }
}
