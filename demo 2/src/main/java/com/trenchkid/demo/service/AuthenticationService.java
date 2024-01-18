package com.trenchkid.demo.service;

import com.trenchkid.demo.enumeration.Role;
import com.trenchkid.demo.model.Owner;
import com.trenchkid.demo.repository.OwnerRepository;
import com.trenchkid.demo.security.AuthenticationRequest;
import com.trenchkid.demo.security.AuthenticationResponse;
import com.trenchkid.demo.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var owner = Owner.builder()
                .ownerName(request.getName())
                .ownerUserName(request.getUserName())
                .ownerEmail(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.OWNER)
                .createdDate(new Date(System.currentTimeMillis()))
                .build();
        ownerRepository.save(owner);
        var jwtToken = jwtService.generateToken(owner);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );
        var owner = ownerRepository.findByOwnerUserName(request.getUserName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(owner);
        return AuthenticationResponse.builder()
                .loggedInUserId(owner.getId())
                .token(jwtToken)
                .build();
    }

}
