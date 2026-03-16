package com.uit.payment.repository;

import com.uit.entity.Order;
import com.uit.entity.TopupHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TopupHistory, String>, JpaSpecificationExecutor<TopupHistory> {

}
