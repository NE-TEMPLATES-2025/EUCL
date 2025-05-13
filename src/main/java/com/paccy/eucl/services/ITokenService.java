package com.paccy.eucl.services;

import com.paccy.eucl.entities.Token;
import com.paccy.eucl.request.PurchaseElectricityRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface ITokenService{
    Token purchaseElectricity(PurchaseElectricityRequest purchaseElectricityRequest);
    String validateToken(String token);
    Page<Token> getAllTokensByMeterNumber(UUID meterNumberId, int page, int size);
}
