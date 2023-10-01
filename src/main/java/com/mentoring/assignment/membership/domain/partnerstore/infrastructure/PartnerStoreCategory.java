package com.mentoring.assignment.membership.domain.partnerstore.infrastructure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PartnerStoreCategory")

public class PartnerStoreCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(11)")
    private Long id;


    @Column(columnDefinition = "CHAR(1)", nullable = false)
    private char field;



}
