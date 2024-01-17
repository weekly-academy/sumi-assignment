package com.mentoring.assignment.membership.domain.barcode.application;

import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.barcode.infrastructure.BarcodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BarcodeServiceImpl implements BarcodeService{

    private final BarcodeRepository barcodeRepository;


    // 바코드 생성 로직
    @Override
    public String createBarcode() {
        String newBarcodeNumber = RandomStringUtils.randomNumeric(10);
//        log.info(newBarcodeNumber);
        return newBarcodeNumber;
    }


    // 바코드가 주어지면, 바코드가 DB에 등록되어있는지 확인
    @Override
    public Boolean validateBarcode(String barcodeNumber) throws Exception {
        Barcode barcode = barcodeRepository.findByBarcodeNumber(barcodeNumber)
                .orElseThrow(() -> new Exception("등록되지 않은 바코드입니다."));

        return true;
    }



}
