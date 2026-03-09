package com.uit.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "E_Cart_Detail")
public class ECartDetail {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID_DETAIL", columnDefinition = "VARCHAR(36)")
    String id;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private ECart cart;
    //private ECart cart;

    @Column(name = "AMOUNT")
    private Float amount;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "PRICE",length = 18)
    private Float price;

    @Column(name = "TOTAL_MONEY",length = 18)
    private Float totalMoney;

    // Getters and setters
}