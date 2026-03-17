package com.uit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@Table(name = "E_USER_WALLET")
@NoArgsConstructor @AllArgsConstructor
public class UserWallet {

    @Id
    @Column(name = "USER_ID", length = 18)
    private String userId;

    @Column(name = "BALANCE_MONEY", length = 18)
    private Number value;
}
