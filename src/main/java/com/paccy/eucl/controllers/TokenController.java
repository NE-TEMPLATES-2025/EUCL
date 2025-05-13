package com.paccy.eucl.controllers;


import com.paccy.eucl.entities.Token;
import com.paccy.eucl.request.PurchaseElectricityRequest;
import com.paccy.eucl.response.ApiResponse;
import com.paccy.eucl.services.impl.TokenServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenServiceImpl tokenService;


    @PostMapping("/purchase")
    public ResponseEntity<ApiResponse<Token>> createToken(
            @RequestBody @Valid PurchaseElectricityRequest purchaseElectricityRequest
            ){

     Token response= tokenService.purchaseElectricity(purchaseElectricityRequest);
     return  new ApiResponse<>("Electricity purchased successfully", HttpStatus.CREATED,response).toResponseEntity();
    }
}
