package com.paccy.eucl.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.paccy.eucl.enums.ETokenStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "token_status")
    private ETokenStatus status= ETokenStatus.NEW;

    @Column(name = "token_value_daya")
    private Long tokenValueDays;

    @CreatedDate
    private LocalDateTime purchased_date;

    @Column(name = "amount",nullable = false)
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonBackReference
    private MeterNumber meterNumber;
}
