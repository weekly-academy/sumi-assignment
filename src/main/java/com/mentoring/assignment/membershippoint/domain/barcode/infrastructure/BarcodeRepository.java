package com.mentoring.assignment.membershippoint.domain.barcode.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Long> { // Entity, type of PK
    Optional<Barcode> findByBarcodeNumber(String barcode);
}
