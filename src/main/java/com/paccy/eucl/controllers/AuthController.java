package com.paccy.eucl.controllers;


import com.paccy.eucl.request.LoginRequest;
import com.paccy.eucl.response.ApiResponse;
import com.paccy.eucl.response.AuthResponse;
import com.paccy.eucl.services.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody LoginRequest loginRequest
            ){
        AuthResponse response= authService.login(loginRequest);
        return new ApiResponse<>("User logged in successfully", HttpStatus.OK,response).toResponseEntity();
    }
}
