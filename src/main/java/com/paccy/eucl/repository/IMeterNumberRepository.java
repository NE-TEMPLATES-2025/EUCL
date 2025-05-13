package com.paccy.eucl.repository;

import com.paccy.eucl.entities.MeterNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IMeterNumberRepository extends JpaRepository<MeterNumber, UUID> {
    Optional<MeterNumber> findMeterNumberByMeterNumber(String meterNumber);
}
