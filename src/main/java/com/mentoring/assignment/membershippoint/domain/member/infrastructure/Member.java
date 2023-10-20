package com.mentoring.assignment.membershippoint.domain.member.infrastructure;

import com.mentoring.assignment.membershippoint.domain.barcode.infrastructure.Barcode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(11)")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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
}

