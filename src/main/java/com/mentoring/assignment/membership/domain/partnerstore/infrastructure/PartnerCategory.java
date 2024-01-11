package com.mentoring.assignment.membership.domain.partnerstore.infrastructure;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "partner_category")
public class PartnerCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(11)")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Field field;

    @Builder
    public PartnerCategory(Field field){
        this.field = field;
    }
}
