package com.trenchkid.demo.security;

import com.trenchkid.demo.model.Housing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessResponse {

    private AuthenticationResponse authenticationResponse;
    private List<Housing> housingList;

}
