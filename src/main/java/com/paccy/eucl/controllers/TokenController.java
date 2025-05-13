package com.paccy.eucl.controllers;


import com.paccy.eucl.entities.Token;
import com.paccy.eucl.request.PurchaseElectricityRequest;
import com.paccy.eucl.response.ApiResponse;
import com.paccy.eucl.services.impl.TokenServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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


    @GetMapping("/{tokenString}")
    public ResponseEntity<ApiResponse<String>> validateToken(
            @PathVariable("tokenString") String tokenString
    ){
      String response = tokenService.validateToken(tokenString);
      return new ApiResponse<>("Token validated successfully", HttpStatus.OK, response).toResponseEntity();
    }


    @GetMapping("/search/{meterNumberId}")
    public ResponseEntity<ApiResponse<Page<Token>>> getTokensByMeterNumber(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable(name = "meterNumberId") UUID meterNumberId
    ){
        Page<Token> response= tokenService.getAllTokensByMeterNumber(meterNumberId,page,size);
        return new ApiResponse<>("Tokens found", HttpStatus.OK, response).toResponseEntity();
    }
}
