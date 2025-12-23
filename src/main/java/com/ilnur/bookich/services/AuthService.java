package com.ilnur.bookich.services;

import com.ilnur.bookich.dtos.AuthResponseDTO;
import com.ilnur.bookich.dtos.UserLoginDTO;
import com.ilnur.bookich.dtos.UserRegistrationDTO;
import com.ilnur.bookich.entities.User;
import com.ilnur.bookich.exceptions.UserAlreadyExistsException;
import com.ilnur.bookich.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    /*
     these two beans are injected using the @RequiredArgsConstructor, this kind of constructor of Lombok
     includes only final and @NonNull marked fields of a class
     AllArgsConstructor includes every field except static ones
     */
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(UserRegistrationDTO userDTO) throws UserAlreadyExistsException {
        if(userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("User with this name already exists");
        }

        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Phone number is already in use.");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDistrict(userDTO.getDistrict());

        userRepository.save(user);

        return new AuthResponseDTO("registration-success-placeholder");
    }

    public AuthResponseDTO login(UserLoginDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );

        // If code reaches here, the user is 100% valid.
        return new AuthResponseDTO("login-success-placeholder");
    }
}
