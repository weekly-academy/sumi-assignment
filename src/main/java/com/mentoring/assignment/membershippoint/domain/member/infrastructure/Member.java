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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barcode_id", nullable = false)
    private Barcode barcode;

//    @Builder
//    public Member(Barcode barcode) {
//
//        Assert.notNull(barcode, "currentPoint must not be null");
//
//        this.barcode = barcode;
//    }
}
