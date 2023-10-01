package com.mentoring.assignment.membership.domain.barcode.infrastructure;


import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Barcode")
public class Barcode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barcode_id", columnDefinition = "BIGINT(11)")
    private Long id;

    @Column(name = "barcode_number", columnDefinition = "CHAR(10)")
    private String barcodeNumber;


    @Builder
    public Barcode(Long id, String barcodeNumber) {
        this.id = id;
        this.barcodeNumber = barcodeNumber;
    }

    public void updateBarcode(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }





}
