package com.mentoring.assignment.membershippoint.domain.barcode.infrastructure;


import com.mentoring.assignment.membershippoint.domain.member.infrastructure.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public Barcode(String barcodeNumber, Member member) {
        this.barcodeNumber = barcodeNumber;
        this.member = member;
    }

}
