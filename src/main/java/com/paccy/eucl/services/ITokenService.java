package com.paccy.eucl.services;

import com.paccy.eucl.entities.Token;
import com.paccy.eucl.request.PurchaseElectricityRequest;
import org.springframework.stereotype.Service;



public interface ITokenService{
    Token purchaseElectricity(PurchaseElectricityRequest purchaseElectricityRequest);
}
