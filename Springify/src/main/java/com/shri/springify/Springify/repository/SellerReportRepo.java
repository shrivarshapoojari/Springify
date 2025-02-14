package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepo  extends JpaRepository<SellerReport,Long> {

    SellerReport findBySellerId(Long sellerId);
}
