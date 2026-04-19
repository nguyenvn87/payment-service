package com.uit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "E_USER_WALLET")
@NoArgsConstructor @AllArgsConstructor
public class UserWallet {

    @Id
    @Column(name = "USER_ID", length = 18)
    private String userId;

    @Column(name = "balance_money", nullable = false)
    private BigDecimal balanceMoney;
}
