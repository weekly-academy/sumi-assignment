package com.mentoring.assignment.membership.domain.barcode.infrastructure;


import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;


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

    public void assignMember(Member member) {
        this.member = member;
    }


    // 랜덤 문자열을 만드는 작업을 서비스에서 할 필요가 없음 -> Entity 내에 만들거나 따로 클래스를 만들어줘야함
    public void create() {
        String randomNumber = RandomStringUtils.randomNumeric(10);

        // 앞 자릿수가 0이 올 경우 존재
        while (Long.valueOf(randomNumber).toString().length() < 10) {
            randomNumber = RandomStringUtils.randomNumeric(10);
        }

        this.barcodeNumber = randomNumber;
    }


}
