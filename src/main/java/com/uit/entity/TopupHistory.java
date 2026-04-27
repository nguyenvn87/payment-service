package com.uit.entity;

import com.uit.common.constant.ProjectEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TOPTUP_HISTORY")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TopupHistory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "TOPUP_ID", columnDefinition = "VARCHAR(36)")
    String topupId;

    //@OneToOne
    //@JoinColumn(name = "ORDER_ID")
    //private Order order;
    @Column(name = "ORDER_ID",length = 36)
    private String orderId;

    @Column(name = "BILL_CD",length = 18)
    private String billCd;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "MONEY_AMOUNT",length = 18)
    private Double amountValue;

    @Column(name = "POINT_ADDED",length = 18)
    private Double pointAdded;

    @Column(name = "CONTENT_MSG",length = 150)
    private String message;

    @Column(name = "REF_CODE", length = 10)
    private String ref;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROJECT_TYPE", length = 30)
    private ProjectEnums projectType;

}