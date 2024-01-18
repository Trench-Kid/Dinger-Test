package com.trenchkid.demo.controller;

import com.trenchkid.demo.security.AuthenticationRequest;
import com.trenchkid.demo.security.AuthenticationResponse;
import com.trenchkid.demo.security.LoginSuccessResponse;
import com.trenchkid.demo.security.RegisterRequest;
import com.trenchkid.demo.service.AuthenticationService;
import com.trenchkid.demo.service.HousingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final HousingService housingService;

    @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<AuthenticationResponse> register(@Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccessResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        var authResponse = authService.authenticate(request);
        return ResponseEntity.ok(
                LoginSuccessResponse.builder()
                        .authenticationResponse(authResponse)
                        .housingList(housingService.findHousingByOwnerId(authResponse.getLoggedInUserId(), 0, 10))
                        .build()
        );
    }
}
