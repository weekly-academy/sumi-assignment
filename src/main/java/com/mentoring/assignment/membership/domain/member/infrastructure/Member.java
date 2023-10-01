package com.mentoring.assignment.membership.domain.member.infrastructure;

import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;
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
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(11)")
    private Long id;

    // CascadeType.PERSIST: 부모 객체를 저장할 때 자식 객체도 함꼐 저장됨
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "barcode_id", nullable = false)
    private Barcode barcode;

    @OneToMany(mappedBy = "member", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<MemberPoint> memberPoints;

    @Builder
    public Member(Barcode barcode) {

        Assert.notNull(barcode, "currentPoint must not be null");

        this.barcode = barcode;
    }



}
