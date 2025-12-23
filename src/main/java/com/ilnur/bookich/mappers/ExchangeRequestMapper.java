package com.ilnur.bookich.mappers;

import com.ilnur.bookich.dtos.ExchangeResponseDTO;
import com.ilnur.bookich.entities.ExchangeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ExchangeRequestMapper {
    @Mapping(source="offeredBook.id", target = "offeredBookId")
    @Mapping(source="requestedBook.id", target = "requestedBookId")
    ExchangeResponseDTO toResponseDTO(ExchangeRequest request);
}
