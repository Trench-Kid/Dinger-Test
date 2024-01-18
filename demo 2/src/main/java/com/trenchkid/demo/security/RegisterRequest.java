package com.trenchkid.demo.security;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name must not be empty!")
    private String name;
    @NotBlank(message = "Username must not be empty!")
    private String userName;
    @NotBlank(message = "Email must not be empty!")
    private String email;
    @NotBlank(message = "Password must not be empty!")
    private String password;
}
