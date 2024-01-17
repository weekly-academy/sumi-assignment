package com.mentoring.assignment.membership.domain.partnerstore.application;


import com.mentoring.assignment.membership.domain.partnerstore.infrastructure.PartnerStore;

public interface PartnerService {
    PartnerStore validatePartner(Long partnerId) throws Exception;


}
