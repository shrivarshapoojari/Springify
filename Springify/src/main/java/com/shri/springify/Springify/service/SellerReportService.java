package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Long sellerId) throws Exception;

    SellerReport updateSellerReport(SellerReport sellerReport);
}
