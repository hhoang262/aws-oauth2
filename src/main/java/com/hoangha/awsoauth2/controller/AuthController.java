package com.hoangha.awsoauth2.controller;

import com.hoangha.awsoauth2.common.MessageResponse;
import com.hoangha.awsoauth2.config.JwtConfiguration;
import com.hoangha.awsoauth2.dto.JwtResponse;
import com.hoangha.awsoauth2.dto.UserCredentials;
import com.hoangha.awsoauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final UserService userDetailsService;
    private final PasswordEncoder encoder;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody UserCredentials userCredentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtConfiguration.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                "Bearer",
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody UserCredentials userCredentials) {

//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }

        userDetailsService.findUsername(userCredentials.getUsername());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
