package com.mentoring.assignment.membership.domain.barcode.application;


public interface BarcodeService {

    String createBarcode();

    Boolean validateBarcode(String barcodeNumber) throws Exception;

}
