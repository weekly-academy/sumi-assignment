package com.mentoring.assignment.membership.domain.member.infrastructure;


import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "member_point")
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private PartnerCategory partnerCategory;

    public MemberPoint(int currentPoint, Member member, PartnerCategory partnerCategory) {

        Assert.notNull(currentPoint, "currentPoint must not be null");
        Assert.notNull(member, "member must not be null");
        Assert.notNull(partnerCategory, "partnerStoreCategory must not be null");

        this.currentPoint = currentPoint;
        this.member = member;
        this.partnerCategory = partnerCategory;
    }

    public void updatePoint(Integer currentPoint) {
        this.currentPoint = currentPoint;
    }

}
