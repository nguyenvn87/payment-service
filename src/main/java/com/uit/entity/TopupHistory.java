package com.uit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TOPTUP_HISTORY")
@NoArgsConstructor @AllArgsConstructor
public class TopupHistory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "TOPUP_ID", columnDefinition = "VARCHAR(36)")
    String topupId;

    @OneToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "MONEY_AMOUNT",length = 18)
    private double amountValue;

    @Column(name = "POINT_ADDED",length = 10)
    private double pointAdded;

    @Column(name = "CONTENT_MSG",length = 150)
    private String message;

}