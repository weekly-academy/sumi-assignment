package com.mentoring.assignment.membership.domain.member.infrastructure;

import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStoreCategory;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "MemberPoint")
public class MemberPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id", columnDefinition = "BIGINT(11)")
    private Long id;

    @Builder.Default
    // default값 point 0 설정
    @Column(name = "current_point", columnDefinition = "BIGINT(20)", nullable = false)
    private int currentPoint = 0;

    // CasCade 설정을 해주고 싶은데 manyToOne은 반대가 되므로 하기 어려움
    // 1. 양방향 설정 2. 직접 설정 (create만 가능) -> 양방향 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private PartnerStoreCategory partnerStoreCategory;

    public MemberPoint(int currentPoint, Member member, PartnerStoreCategory partnerStoreCategory) {

        Assert.notNull(currentPoint, "currentPoint must not be null");
        Assert.notNull(member, "member must not be null");
        Assert.notNull(partnerStoreCategory, "partnerStoreCategory must not be null");

        this.currentPoint = currentPoint;
        this.member = member;
        this.partnerStoreCategory = partnerStoreCategory;
    }

    // dto로 변환로직 만들기

}
