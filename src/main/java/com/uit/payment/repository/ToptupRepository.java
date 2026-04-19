package com.uit.payment.repository;

import com.uit.entity.TopupHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToptupRepository extends JpaRepository<TopupHistory, String> {
}
