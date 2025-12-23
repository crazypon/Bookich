package com.ilnur.bookich.services;

import com.ilnur.bookich.dtos.ExchangeRequestDTO;
import com.ilnur.bookich.entities.Book;
import com.ilnur.bookich.entities.ExchangeRequest;
import com.ilnur.bookich.entities.User;
import com.ilnur.bookich.repositories.BookRepository;
import com.ilnur.bookich.repositories.ExchangeRequestRepository;
import com.ilnur.bookich.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ExchangeRequestService {

    private final ExchangeRequestRepository exchangeRequestRepository;
    private final UserRepository userRepository;
    private final UserContextService userContextService;
    private final BookRepository bookRepository;

    public void createRequest(ExchangeRequestDTO dto) {
        User receiver = userRepository.findByPublicId(dto.getReceiverId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No Such Receiver With This ID"
                )
        );
        User initiator = userContextService.getCurrentUser();

        if(receiver.equals(initiator)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You cannot Exchange with Yourself"
            );
        }

        Book requestedBook = bookRepository.findById(dto.getRequestedBookId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such Book to Exchange")
        );
        Book offeredBook = bookRepository.findById(dto.getOfferedBookId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such Book to Exchange")
        );

        ExchangeRequest request = new ExchangeRequest();

        request.setType(dto.getExchangeType());
        request.setInitiator(initiator);
        request.setReceiver(receiver);
        request.setRequestedBook(requestedBook);
        request.setOfferedBook(offeredBook);

        exchangeRequestRepository.save(request);

    }

}
