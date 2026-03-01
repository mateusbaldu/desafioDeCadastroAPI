package com.desafio.cadastro.mappers;

import com.desafio.cadastro.models.Address;
import com.desafio.cadastro.models.dtos.address.AddressCreate;
import com.desafio.cadastro.models.dtos.address.AddressResponse;
import com.desafio.cadastro.models.dtos.address.AddressUpdate;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    @Mapping(source = "street", target = "street")
    Address createToEntity(AddressCreate dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateToEntity(AddressUpdate dto, @MappingTarget Address entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromCreate(AddressCreate dto, @MappingTarget Address entity);

    AddressResponse entityToResponse(Address address);
}
