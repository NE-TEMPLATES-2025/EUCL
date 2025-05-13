package com.paccy.eucl.repository;

import com.paccy.eucl.entities.MeterNumber;
import com.paccy.eucl.entities.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.Optional;

@Repository
public interface ITokenRepository  extends JpaRepository<Token,Integer> {
    Token findByMeterNumber(MeterNumber meterNumber);
    Optional<Token> findTopByMeterNumber_MeterNumber(String meterNumber);
    Page<Token> findAllByMeterNumber_MeterNumber(String meterNumber, Pageable pageable);
    Optional<Token> findByToken(String token);

    Page<Token> findAllByMeterNumber(MeterNumber meterNumber, Pageable pageable);
}
