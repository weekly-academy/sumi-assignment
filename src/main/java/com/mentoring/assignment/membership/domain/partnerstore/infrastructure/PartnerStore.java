package com.mentoring.assignment.membership.domain.partnerstore.infrastructure;


import com.mentoring.assignment.membership.domain.partnercategory.infrastructure.PartnerCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "partner_store")
public class PartnerStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(11)")
    private Long id;


    @Column(name = "partner_name", columnDefinition = "VARCHAR(20)", nullable = false)
    private String partnerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private PartnerCategory partnerCategory;

    @Builder
    public PartnerStore(String partnerName, PartnerCategory partnerCategory) {
        this.partnerName = partnerName;
        this.partnerCategory = partnerCategory;
    }


}
