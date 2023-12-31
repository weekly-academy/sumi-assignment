package com.mentoring.assignment.membership.domain.partnerstore.infrastructure;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PartnerStore")

public class PartnerStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(11)")
    private Long id;


    @Column(name = "partner_name", columnDefinition = "VARCHAR(20)", nullable = false)
    private String partnerName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private PartnerStoreCategory partnerStoreCategory;

    @Builder
    public PartnerStore(String partnerName, PartnerStoreCategory partnerStoreCategory){
        this.partnerName = partnerName;
        this.partnerStoreCategory = partnerStoreCategory;
    }

}
