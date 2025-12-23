package com.ilnur.bookich.controllers;

import com.ilnur.bookich.dtos.ExchangeRequestDTO;
import com.ilnur.bookich.dtos.ExchangeResponseDTO;
import com.ilnur.bookich.dtos.RequestStatusDTO;
import com.ilnur.bookich.services.ExchangeRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeRequestService exchangeRequestService;

    @PostMapping("/request")
    public ResponseEntity<String> createRequest(@RequestBody ExchangeRequestDTO request) {
        exchangeRequestService.createRequest(request);
        return ResponseEntity.ok("Exchange Request Created Successfully");
    }

    @GetMapping("/incoming")
    public ResponseEntity<List<ExchangeResponseDTO>> getIncomingRequests() {
        List<ExchangeResponseDTO> incoming = exchangeRequestService.getIncoming();
        return ResponseEntity.ok(incoming);
    }

    @GetMapping("/outgoing")
    public ResponseEntity<List<ExchangeResponseDTO>> getOutGoingRequests() {
        List<ExchangeResponseDTO> outgoing = exchangeRequestService.getOutgoing();
        return ResponseEntity.ok(outgoing);
    }

    @PutMapping("/{reqId}/status")
    public ResponseEntity<String> changeRequestStatus(@PathVariable Long reqId,
                                                      @Valid @RequestBody RequestStatusDTO statusDTO) {
        exchangeRequestService.updateExchangeStatus(reqId, statusDTO);
        return ResponseEntity.ok("Status Updated Successfully");
    }

}
