package com.mentoring.assignment.membership.domain.pointhistory.infrastructure;

import com.mentoring.assignment.membership.domain.barcode.infrastructure.Barcode;

import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerCategory;
import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_history")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id",columnDefinition = "BIGINT(11)")
    private Long id;


    @Column(name = "approved_at", columnDefinition = "TIMESTAMP", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;


    @Column(nullable = false, columnDefinition = "BIGINT(10)")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private PartnerCategory partnerCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private PartnerStore partnerStore;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barcode_id", nullable = false)
    private Barcode barcode;


    @Builder
    public PointHistory(LocalDateTime approvedAt, Type type, Integer amount, PartnerCategory partnerCategory, PartnerStore partnerStore, Barcode barcode) {
        this.approvedAt = approvedAt;
        this.type = type;
        this.amount = amount;
        this.partnerCategory = partnerCategory;
        this.partnerStore = partnerStore;
        this.barcode = barcode;
    }

}
