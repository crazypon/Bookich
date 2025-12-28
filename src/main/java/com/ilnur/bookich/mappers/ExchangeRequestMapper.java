package com.ilnur.bookich.mappers;

import com.ilnur.bookich.dtos.ExchangeRequestDTO;
import com.ilnur.bookich.dtos.ExchangeResponseDTO;
import com.ilnur.bookich.entities.Book;
import com.ilnur.bookich.entities.ExchangeRequest;
import com.ilnur.bookich.entities.User;
import com.ilnur.bookich.repositories.BookRepository;
import com.ilnur.bookich.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;


@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class ExchangeRequestMapper {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Mapping(source="offeredBook.id", target = "offeredBookId")
    @Mapping(source="requestedBook.id", target = "requestedBookId")
    public abstract ExchangeResponseDTO toResponseDTO(ExchangeRequest request);

    /*
    Mapping id and object
     */

    @Mapping(source = "offeredBookId", target = "offeredBook", qualifiedByName = "idToBook")
    @Mapping(source = "requestedBookId", target = "requestedBook", qualifiedByName = "idToBook")
    @Mapping(source = "receiverId", target = "receiver", qualifiedByName = "idToReceiver")
    public abstract ExchangeRequest toExchangeRequest(ExchangeRequestDTO dto);

    @Named("idToBook")
    protected Book mapBook(Long id) {
        if(id == null) return null;
        return bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such Book")
        );
    }

    @Named("idToReceiver")
    protected User mapReceiver(Long id) {
        if(id == null) return null;
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such User")
        );
    }

}
