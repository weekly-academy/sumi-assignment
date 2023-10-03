package com.mentoring.assignment.membershippoint.domain.barcode.application;


import com.mentoring.assignment.membershippoint.domain.barcode.infrastructure.Barcode;

public interface BarcodeService {

    String createBarcode();

    Barcode validateBarcode(String barcodeNumber) throws Exception;

}
