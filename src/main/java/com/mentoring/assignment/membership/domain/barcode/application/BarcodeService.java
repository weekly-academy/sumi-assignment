package com.mentoring.assignment.membership.domain.barcode.application;

import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.barcode.infrastructure.BarcodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BarcodeService {

    private final BarcodeRepository barcodeRepository;


    // 바코드 생성 로직
    public String createBarcode() {
        // 앞 자릿수가 0이면 request 보낼 때 에러가 남
       String newBarcodeNumber = RandomStringUtils.randomNumeric(10);

        // 앞 자릿수가 0이 올 경우 존재
        while (Long.valueOf(newBarcodeNumber).toString().length() < 10) {
            newBarcodeNumber = RandomStringUtils.randomNumeric(10);
        }

        log.info(newBarcodeNumber);
        return newBarcodeNumber;
    }


    // 바코드가 주어지면, 바코드가 DB에 등록되어있는지 확인
    public Barcode validateBarcode(String barcodeNumber) throws Exception {
        Barcode barcode = barcodeRepository.findByBarcodeNumber(barcodeNumber)
                .orElseThrow(() -> new Exception("등록되지 않은 바코드입니다."));

        return barcode;
    }

    // 바코드 저장 로직
    public void save(Barcode barcode) {
        barcodeRepository.save(barcode);
    }



}
