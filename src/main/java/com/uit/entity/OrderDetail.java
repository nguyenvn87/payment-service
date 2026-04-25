package com.uit.entity;

import com.uit.common.constant.ServiceTypeEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "E_ORDER_DETAIL")
@NoArgsConstructor @AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID_DETAIL", columnDefinition = "VARCHAR(36)")
    String id;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "PRICE",length = 18)
    private double price;

    @Column(name = "SERVICE_ID")
    private String serviceId;

    @Column(name = "SERVICE_TYPE")
    private ServiceTypeEnums serviceType;

    // Tên dịch vụ: gói 1 năm, 3 năm..., tên podcast
    @Column(name = "SERVICE_NM",length = 150)
    private String serviceName;

}