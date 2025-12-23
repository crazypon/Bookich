package com.ilnur.bookich.services;

import com.ilnur.bookich.dtos.ExchangeRequestDTO;
import com.ilnur.bookich.dtos.ExchangeResponseDTO;
import com.ilnur.bookich.entities.Book;
import com.ilnur.bookich.entities.ExchangeRequest;
import com.ilnur.bookich.entities.User;
import com.ilnur.bookich.mappers.ExchangeRequestMapper;
import com.ilnur.bookich.repositories.BookRepository;
import com.ilnur.bookich.repositories.ExchangeRequestRepository;
import com.ilnur.bookich.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ExchangeRequestService {

    private final ExchangeRequestRepository exchangeRequestRepository;
    private final UserRepository userRepository;
    private final UserContextService userContextService;
    private final BookRepository bookRepository;
    private final ExchangeRequestMapper exchangeRequestMapper;

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

        if(!offeredBook.getOwner().getId().equals(initiator.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not the owner of offered book");
        }

        ExchangeRequest request = new ExchangeRequest();

        request.setType(dto.getExchangeType());
        request.setInitiator(initiator);
        request.setReceiver(receiver);
        request.setRequestedBook(requestedBook);
        request.setOfferedBook(offeredBook);

        exchangeRequestRepository.save(request);

    }

    @Transactional(readOnly = true) // this is for performance purposes
    public List<ExchangeResponseDTO> getIncoming() {
        User currentUser = userContextService.getCurrentUser();
        List<ExchangeRequest> rawRequests = exchangeRequestRepository.getExchangeRequestsByReceiver(currentUser);

        return rawRequests.stream()
                .map(exchangeRequestMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExchangeResponseDTO> getOutgoing() {
        User currentUser = userContextService.getCurrentUser();
        List<ExchangeRequest> rawRequests = exchangeRequestRepository.getExchangeRequestsByInitiator(currentUser);

        return rawRequests.stream()
                .map(exchangeRequestMapper::toResponseDTO)
                .toList();
    }


}
