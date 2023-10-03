package com.mentoring.assignment.membershippoint.domain.member.infrastructure;

import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerCategory;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "member_pont")
public class MemberPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id", columnDefinition = "BIGINT(11)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private PartnerCategory partnerCategory;

    @Builder.Default
    // default값 point 0 설정
    @Column(name = "current_point", columnDefinition = "BIGINT(20)", nullable = false)
    private Integer currentPoint = 0;

    public MemberPoint(Integer currentPoint, Member member, PartnerCategory partnerCategory) {

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
