package com.ilnur.bookich.controllers;

import com.ilnur.bookich.dtos.ExchangeRequestDTO;
import com.ilnur.bookich.services.ExchangeRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeRequestService exchangeRequestService;

    @PostMapping("/request")
    public ResponseEntity<String> createRequest(@RequestBody ExchangeRequestDTO request) {
        exchangeRequestService.createRequest(request);
    }

}
