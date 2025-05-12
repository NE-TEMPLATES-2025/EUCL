package com.paccy.eucl.response;


import com.paccy.eucl.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private User user;
}
