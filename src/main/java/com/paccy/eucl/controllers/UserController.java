package com.paccy.eucl.controllers;


import com.paccy.eucl.entities.User;
import com.paccy.eucl.request.RegisterRequest;
import com.paccy.eucl.response.ApiResponse;
import com.paccy.eucl.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;


    @PostMapping("/customer/register")
    public ResponseEntity<ApiResponse<User>> createCustomer(
            @RequestBody RegisterRequest registerRequest
            ){
        User response= userService.createCustomer(registerRequest);
        return new ApiResponse<>("Customer Account created successfully", HttpStatus.CREATED,response).toResponseEntity();
    }

    @PostMapping("/admin/register")
    public ResponseEntity<ApiResponse<User>> createAdmin(
            @RequestBody RegisterRequest registerRequest
    ){
        User response= userService.createAdmin(registerRequest);
        return new ApiResponse<>("Admin Account created successfully", HttpStatus.CREATED,response).toResponseEntity();
    }
}
