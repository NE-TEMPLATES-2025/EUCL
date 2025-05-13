package com.paccy.eucl.services;

import com.paccy.eucl.entities.MeterNumber;
import com.paccy.eucl.request.MeterRegistrationRequest;
import org.springframework.data.domain.Page;

public interface IMeterNumberService {
    MeterNumber registerMeterNumber(MeterRegistrationRequest meterRegistrationRequest);
    Page<MeterNumber> getMeterNumbers (int page, int size);
}
