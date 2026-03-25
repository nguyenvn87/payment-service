package com.uit.entity;

import com.uit.common.constant.PaymentStsEnums;
import com.uit.common.constant.PurchaseTypeEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "E_ORDER")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "ORDER_ID", columnDefinition = "VARCHAR(36)")
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String orderId;

    @Column(name = "BILL_CD",length = 36)
    private String billCode;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "TOTAL_MONEY",length = 18)
    private double totalMoney;

    @Column(name = "USER_ID", length = 18)
    private String userId;

    @Column(name = "PHONE", length = 18)
    private String phone;

    @Column(name = "PAY_MONEY")
    private double payedMoney;

    @Column(name = "PAY_STS", length = 18)
    private PaymentStsEnums payStatus;

    // Mua bằng tiền hoặc bằng điểm thưởng
    @Column(name = "PURCHASE_TYPE", length = 10)
    private PurchaseTypeEnums purchaseType;

    @Column(name = "REF_CODE", length = 10)
    private String ref;

    @OneToMany(mappedBy = "cart")
    private List<OrderDetail> details = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TopupHistory transaction;
}
