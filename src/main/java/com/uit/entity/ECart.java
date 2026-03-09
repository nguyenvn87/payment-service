package com.uit.entity;

import com.uit.common.constant.PaymentStsEnums;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "E_Cart")
public class ECart {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "BILL_CD",length = 36)
    private String billCode;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "TOTAL_MONEY",length = 18)
    private Number totalMoney;

    @Column(name = "USER_ID", length = 18)
    private String userId;

    @Column(name = "PHONE", length = 18)
    private String phone;

    @Column(name = "PAY_MONEY", length = 18)
    private Number payedMoney;

    @Column(name = "PAY_STS", length = 18)
    private PaymentStsEnums payStatus;

    @Column(name = "REF", length = 10)
    private String ref;

    @OneToMany(mappedBy = "cart")
    private List<ECartDetail> details = new ArrayList<>();
    // Getters and setters
}
