package com.paccy.eucl.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseElectricityRequest {


    @NotBlank
      private String meterNumber;

//    @Pattern(regexp = "^\\d*[1-9]\\d*00$",message = "The allowed amount is 100,200,300,400,etc")
    private Long amount;
}
