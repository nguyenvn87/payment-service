package com.uit.entity;

import com.uit.common.constant.PaymentStsEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "E_USER_POINT")
@NoArgsConstructor @AllArgsConstructor
public class UserPoint {

    @Id
    @Column(name = "USER_ID", length = 18)
    private String userId;

    @Column(name = "BALANCE_MONEY", length = 18)
    private Number value;
}
