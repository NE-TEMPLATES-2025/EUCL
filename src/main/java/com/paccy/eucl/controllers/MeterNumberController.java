package com.paccy.eucl.controllers;


import com.paccy.eucl.entities.MeterNumber;
import com.paccy.eucl.request.MeterRegistrationRequest;
import com.paccy.eucl.response.ApiResponse;
import com.paccy.eucl.services.impl.MeterNumberServiceImpl;
import io.micrometer.core.instrument.Meter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meter-number")
@RequiredArgsConstructor
public class MeterNumberController {


    private final MeterNumberServiceImpl meterNumberService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MeterNumber>> registerMeterNumber(
            @RequestBody @Valid MeterRegistrationRequest meterRegistrationRequest
            ){
        MeterNumber response= meterNumberService.registerMeterNumber(meterRegistrationRequest);
        return new ApiResponse<>("Meter number registered successfully", HttpStatus.CREATED,response).toResponseEntity();
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<MeterNumber>>> getMeterNumbers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){

        Page<MeterNumber> response=meterNumberService.getMeterNumbers(page,size);
        return new ApiResponse<>("Meter numbers retrieved successfully", HttpStatus.OK,response).toResponseEntity();
    }
}
