package com.desafio.cadastro.mappers;

import com.desafio.cadastro.models.Address;
import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.models.dtos.address.AddressResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    void createToEntity(AddressCreate dto, @MappingTarget Address address);
    AddressResponse entityToResponse(Address address);
}
