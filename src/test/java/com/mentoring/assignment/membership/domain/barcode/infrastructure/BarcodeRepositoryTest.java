package com.mentoring.assignment.membership.domain.barcode.infrastructure;

import com.mentoring.assignment.membership.domain.member.infrastructure.Member;
import com.mentoring.assignment.membership.domain.member.infrastructure.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties") //test용 properties 파일 설정
@DataJpaTest
// DataJpaTest : JPA 관련 테스트 설정만 로드, 실제 데이터베이스가 아닌 내장 데이터베이스 사용
// -> 그래서 test용 h2 DB 따로 생성
// 기본적으로 @Transactional 어노테이션 포함 -> 테스트 완료시 자동으로 롤백

// 에러 -> test h2 database 버전을 낮춰줬더니 해결
class BarcodeRepositoryTest {

    @Autowired
    private BarcodeRepository barcodeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("성공_바코드번호로_바코드_찾기")
    void findByBarcodeNumber() {
        // given : 시나리오 진행에 필요한 값 설정
        // builder를 활용해서 이렇게 서비스 작성하는게.. 테스트 코드 작성 맞는지?

        Barcode barcode = Barcode.builder()
                .barcodeNumber("123456789")
                .build();


        Member member = Member.builder()
                .barcode(barcode)
                .build();

        barcode.assignMember(member);

        memberRepository.save(member);
        Barcode savedBarcode = barcodeRepository.save(barcode);

        // when : 테스트하고자하는 행동 명시
        Barcode findBarcode = barcodeRepository.findByBarcodeNumber(barcode.getBarcodeNumber()).get();

        // then : 테스트를 통해 도출된 결과 검증
        Assertions.assertSame(savedBarcode, findBarcode);
        Assertions.assertEquals(savedBarcode.getMember().getId(),findBarcode.getMember().getId());


    }
}