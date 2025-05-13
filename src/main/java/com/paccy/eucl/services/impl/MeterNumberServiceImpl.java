package com.paccy.eucl.services.impl;


import com.paccy.eucl.entities.MeterNumber;
import com.paccy.eucl.entities.User;
import com.paccy.eucl.exceptions.BadRequestException;
import com.paccy.eucl.repository.IMeterNumberRepository;
import com.paccy.eucl.repository.IUserRepository;
import com.paccy.eucl.request.MeterRegistrationRequest;
import com.paccy.eucl.services.IMeterNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeterNumberServiceImpl implements IMeterNumberService {
    private final IUserRepository userRepository;
    private final IMeterNumberRepository meterNumberRepository;


    @Override
    public MeterNumber registerMeterNumber(MeterRegistrationRequest meterRegistrationRequest) {
        Optional<User> _user= this.userRepository.findById(meterRegistrationRequest.getCustomerId());
        if(_user.isEmpty()){
            throw  new BadRequestException("Customer with id "+meterRegistrationRequest.getCustomerId()+" not found");
        }
        User user= _user.get();

//        Check if the meter number already exists
        Optional<MeterNumber> existingMeterNumber= meterNumberRepository.findMeterNumberByMeterNumber(meterRegistrationRequest.getMeterNumber());
        if(existingMeterNumber.isPresent()){
            throw new BadRequestException("Meter number already exists,try register again another one");
        }

        MeterNumber meterNumber = MeterNumber
                .builder()
                .meterNumber(meterRegistrationRequest.getMeterNumber())
                .user(user)
                .build();

        return meterNumberRepository.save(meterNumber);
    }

    @Override
    public Page<MeterNumber> getMeterNumbers(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        return  meterNumberRepository.findAll(pageable);
    }
}
