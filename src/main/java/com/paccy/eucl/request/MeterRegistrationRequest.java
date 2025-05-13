package com.paccy.eucl.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MeterRegistrationRequest {

    @NotEmpty(message = "Meter number is required")
    @NotBlank(message = "Meter number is required")
    @Pattern(regexp = "^(?!.*(.).*\\1)\\d{6}$",message = "Meter number should be exactly 6 unique numbers")
    private String meterNumber;
    private UUID customerId;
}
