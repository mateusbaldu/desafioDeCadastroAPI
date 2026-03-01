package com.desafio.cadastro.controllers;

import com.desafio.cadastro.enums.PetSex;
import com.desafio.cadastro.enums.PetType;
import com.desafio.cadastro.models.dtos.pets.PetCreate;
import com.desafio.cadastro.models.dtos.pets.PetResponse;
import com.desafio.cadastro.models.dtos.pets.PetSearchFilter;
import com.desafio.cadastro.models.dtos.pets.PetUpdate;
import com.desafio.cadastro.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<PetResponse>> search(
            @RequestParam PetType petType,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) PetSex sex,
            @RequestParam(required = false) Float age,
            @RequestParam(required = false) Float weight,
            @RequestParam(required = false) String race,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street
    ) {
        return ResponseEntity.ok(petService.search(
                new PetSearchFilter(petType, name, sex, age, weight, race, city, street)
        ));
    }

    @PostMapping
    public ResponseEntity<PetResponse> create(@RequestBody @Valid PetCreate dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.create(dto));
    }

    @PutMapping
    public ResponseEntity<PetResponse> update(@RequestBody @Valid PetUpdate dto) {
        return ResponseEntity.ok(petService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
