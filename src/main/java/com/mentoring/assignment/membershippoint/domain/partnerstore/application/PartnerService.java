package com.mentoring.assignment.membershippoint.domain.partnerstore.application;
import com.mentoring.assignment.membershippoint.domain.partnerstore.infrastructure.PartnerStore;

public interface PartnerService {
    PartnerStore validatePartner(Long partnerId) throws Exception;





}
