package com.mentoring.assignment.membership.domain.barcode.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface BarcodeRepository extends JpaRepository<Barcode, Long> {
    Optional<Barcode> findByBarcodeNumber(String barcode);
}
