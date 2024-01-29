package com.mentoring.assignment.membership.domain.barcode.application;


import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import com.mentoring.assignment.membership.domain.barcode.infrastructure.BarcodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BarcodeReader {

    private final BarcodeRepository barcodeRepository;


    // 바코드가 주어지면, 바코드가 DB에 등록되어있는지 확인
    public Barcode validateBarcode(String barcodeNumber) throws Exception {
        return barcodeRepository.findByBarcodeNumber(barcodeNumber)
                .orElseThrow(() -> new Exception("등록되지 않은 바코드입니다."));


    }

    public Barcode save(Barcode barcode) {
        return barcodeRepository.save(barcode);
    }

}
