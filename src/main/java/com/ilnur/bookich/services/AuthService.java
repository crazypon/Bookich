package com.ilnur.bookich.services;

import com.ilnur.bookich.constants.ApplicationConstants;
import com.ilnur.bookich.dtos.AuthResponseDTO;
import com.ilnur.bookich.dtos.UserLoginDTO;
import com.ilnur.bookich.dtos.UserRegistrationDTO;
import com.ilnur.bookich.entities.User;
import com.ilnur.bookich.exceptions.UserAlreadyExistsException;
import com.ilnur.bookich.mappers.UserMapper;
import com.ilnur.bookich.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthService {
    /*
     these two beans are injected using the @RequiredArgsConstructor, this kind of constructor of Lombok
     includes only final and @NonNull marked fields of a class
     AllArgsConstructor includes every field except static ones
     */
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//    private final Environment env;
//    private final JwtService jwtService;
//
//    public AuthResponseDTO register(UserRegistrationDTO userDTO) throws UserAlreadyExistsException {
//        if(userRepository.existsByUsername(userDTO.getUsername())) {
//            throw new UserAlreadyExistsException("User with this name already exists");
//        }
//
//        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
//            throw new UserAlreadyExistsException("Phone number is already in use.");
//        }
//
//
//    }
//
//    public AuthResponseDTO login(UserLoginDTO userDTO) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        userDTO.getUsername(),
//                        userDTO.getPassword()
//                )
//        );
//
//    }
}
