package com.desafio.cadastro.mappers;

import com.desafio.cadastro.models.Pets;
import com.desafio.cadastro.models.dtos.pets.PetCreate;
import com.desafio.cadastro.models.dtos.pets.PetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {
    Pets createToEntity(PetCreate dto);

    PetResponse entityToResponse(Pets entity);
}
