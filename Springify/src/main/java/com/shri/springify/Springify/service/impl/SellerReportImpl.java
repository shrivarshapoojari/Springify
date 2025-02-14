package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.model.SellerReport;
import com.shri.springify.Springify.repository.SellerReportRepo;
import com.shri.springify.Springify.service.SellerReportService;
import com.shri.springify.Springify.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerReportImpl implements SellerReportService {

    @Autowired
    private SellerReportRepo sellerReportRepo;
    @Autowired
    private SellerService sellerService;

    @Override
    public SellerReport getSellerReport(Long sellerId) throws Exception {

       Seller seller=sellerService.getSellerById(sellerId);
        SellerReport report= sellerReportRepo.findBySellerId(sellerId);

        if(report==null)
        {
            SellerReport newReport=new SellerReport();
            newReport.setSeller(seller);
            return  sellerReportRepo.save(newReport);
        }
        return  report;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {

        return  sellerReportRepo.save(sellerReport);
    }
}
