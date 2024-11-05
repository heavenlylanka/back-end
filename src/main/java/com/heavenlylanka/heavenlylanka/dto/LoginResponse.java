package com.heavenlylanka.heavenlylanka.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {

    // Getter
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

}
