package com.mentoring.assignment.membershippoint.domain.barcode.infrastructure;


import com.mentoring.assignment.membershippoint.domain.member.infrastructure.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// fix: MySQL 테이블명: 소문자 사용
@Table(name = "barcode")
public class Barcode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(11)")
    private Long id;

    @Column(name = "barcode_number", columnDefinition = "CHAR(10)")
    private String barcodeNumber;

    @OneToOne(mappedBy = "barcode")
    private Member member;


    public void updateBarcode(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

}
