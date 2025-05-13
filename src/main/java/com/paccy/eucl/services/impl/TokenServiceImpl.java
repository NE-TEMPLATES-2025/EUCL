package com.paccy.eucl.services.impl;

import com.paccy.eucl.entities.MeterNumber;
import com.paccy.eucl.entities.Token;
import com.paccy.eucl.enums.ETokenStatus;
import com.paccy.eucl.exceptions.BadRequestException;
import com.paccy.eucl.repository.IMeterNumberRepository;
import com.paccy.eucl.repository.ITokenRepository;
import com.paccy.eucl.request.PurchaseElectricityRequest;
import com.paccy.eucl.services.IMeterNumberService;
import com.paccy.eucl.services.ITokenService;
import com.paccy.eucl.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService {
    private final ITokenRepository tokenRepository;
    private final IMeterNumberRepository meterNumberRepository;

    private final Integer UNIT_PRICE=100;

    @Override
    public Token purchaseElectricity(PurchaseElectricityRequest purchaseElectricityRequest) {
        MeterNumber meterNumber= meterNumberRepository.findMeterNumberByMeterNumber(purchaseElectricityRequest.getMeterNumber())
                .orElseThrow(()-> new BadRequestException("Meter number not found"));
        if ((purchaseElectricityRequest.getAmount() % UNIT_PRICE != 0) && (purchaseElectricityRequest.getAmount() / UNIT_PRICE <1)){
            throw  new BadRequestException("Invalid Amount, please make sure that amount is at least 100 and it is multiple of 100");
        }

//        Generate new token
        String randomToken= Utility.generateRandomToken(16);

//        CALCULATE length of days for token
          Long tokenValueDays= ( purchaseElectricityRequest.getAmount()/ UNIT_PRICE);

//          Check if days equivalent to the amount doesn't exceed 5 years

        Long daysInFiveYears= ChronoUnit.DAYS.between(LocalDateTime.now(), LocalDateTime.now().plusYears(5));
        if((tokenValueDays - daysInFiveYears)>=0){
            throw new BadRequestException("Please reduce the amount money because you can't purchase electricity that will exceed 5 years");
        }

          /*
          If there is existing token which is not ye expired ,
//          there is no need of storing a new token in the database, instead update days for existing one
*/

        Optional<Token> optionalExistingToken = tokenRepository.findTopByMeterNumber_MeterNumber(
                purchaseElectricityRequest.getMeterNumber());

        if (optionalExistingToken.isPresent() && !isElectricityTokenExpired(optionalExistingToken.get())) {
            Token existingToken = optionalExistingToken.get();
            Long currentDaysOfToken = existingToken.getTokenValueDays();
            Long newTokenValueDays = currentDaysOfToken + tokenValueDays;
            if((newTokenValueDays - daysInFiveYears)>=0){
                throw new BadRequestException("Please reduce the amount money because you can't purchase electricity that will exceed 5 years");
            }
            existingToken.setTokenValueDays(newTokenValueDays);
            existingToken.setStatus(ETokenStatus.USED);
            return tokenRepository.save(existingToken);
        }

//        Generate a new token
        else {
            Token token= Token
                    .builder()
                    .token(randomToken)
                    .amount(purchaseElectricityRequest.getAmount())
                    .purchased_date(LocalDateTime.now())
                    .status(ETokenStatus.NEW)
                    .tokenValueDays(tokenValueDays)
                    .meterNumber(meterNumber)
                    .build();
            return tokenRepository.save(token);
        }

    }

    @Override
    public String validateToken(String tokenString) {
    Token token= this.tokenRepository.findByToken(tokenString).orElseThrow(
            ()-> new BadRequestException("Token not found")
    );
    return "Token for " + token.getAmount() + " RWF should light for " + token.getTokenValueDays() + " days as 100RWF lights for 1 day";

    }

    @Override
    public Page<Token> getAllTokensByMeterNumber(UUID meterNumberId, int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        MeterNumber meterNumber=meterNumberRepository.findById(meterNumberId).orElseThrow();
        return tokenRepository.findAllByMeterNumber(meterNumber,pageable).map(
                token -> {
                    String formattedToken = token.getToken().replaceAll("(.{4})","$1-");
                    if (formattedToken.endsWith("-")) {
                        formattedToken = formattedToken.substring(0, formattedToken.length() - 1);
                    }
                    token.setToken(formattedToken);
                    return  token;
                });
    }

    private boolean isElectricityTokenExpired(Token token){
        LocalDateTime getTokenExpirationTime= token.getPurchased_date().plusDays(token.getTokenValueDays());
        return (getTokenExpirationTime.isBefore(LocalDateTime.now()));
    }
}
