package com.mentoring.assignment.membership.domain.member.infrastructure;

import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {
    @Id
    @Column(columnDefinition = "BIGINT(11)")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "barcode_id")
    private Barcode barcode;

    // Builder -> test와  @PostConstruct에서 더미데이터 생성을 위해 필요
    // test에서 id가 필요하므로, 넣어주기
    // 더미데이터에서는 id, barcode 둘다 null 이므로 build만 해주면 됨
    @Builder
    public Member(Long id, Barcode barcode){
        this.id = id;
        this.barcode = barcode;
    }

    // repository의 save메서드를 호출할 때에 @PrePersist가 달린 메서드가 호출
    @PrePersist
    public void generateId() throws Exception {
        if (this.id != null) {
            throw new Exception("id가 있는 경우엔 새로운 id를 생성할 수 없습니다.");
        }

        Long randomId = Long.valueOf(RandomStringUtils.randomNumeric(9));

        // 앞 자릿수가 0이 올 경우 존재
        while (String.valueOf(randomId).length() < 9) {
            randomId = Long.valueOf(RandomStringUtils.randomNumeric(9));
        }
        this.id = randomId;
    }

    // member가 save된 후에 member point도 save 되어야함
//    @PostPersist


    public void assignBarcode(Barcode barcode) {
        this.barcode = barcode;
    }
}

