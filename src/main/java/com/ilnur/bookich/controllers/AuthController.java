package com.ilnur.bookich.controllers;

import com.ilnur.bookich.constants.ApplicationConstants;
import com.ilnur.bookich.dtos.AuthResponseDTO;
import com.ilnur.bookich.dtos.UserLoginDTO;
import com.ilnur.bookich.dtos.UserRegistrationDTO;
import com.ilnur.bookich.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;

//    @PostMapping("/register")
//    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO user) {
//        AuthResponseDTO token = authenticationService.register(user); // this is just a placeholder for our future JWT token
//        return ResponseEntity.ok(token);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody UserLoginDTO user) {
//        AuthResponseDTO token = authenticationService.login(user);
//        return ResponseEntity.ok(token);
//    }
//
//    @PostMapping("/apiLogin")
//    public ResponseEntity<AuthResponseDTO> apiLogin (@RequestBody UserLoginDTO loginRequest) {
//        AuthResponseDTO authResponse = authenticationService.login(loginRequest);
//        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,authResponse.getToken())
//                .body(authResponse);
//    }
}
