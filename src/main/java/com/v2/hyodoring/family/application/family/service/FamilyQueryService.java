package com.v2.hyodoring.family.application.family.service;

import com.v2.hyodoring.family.application.family.exception.FamilyErrorResponse;
import com.v2.hyodoring.family.application.family.exception.FamilyException;
import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.infrastructure.jpa.family.repository.FamilyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FamilyQueryService {
    private final FamilyQueryRepository familyQueryRepository;

    public boolean existsByFamilyCode(String familyCode) {
        return familyQueryRepository.existsByCode(familyCode);
    }

    public Family findByFamilyCode(String familyCode) {
        return familyQueryRepository.findByCode(familyCode)
                .orElseThrow(() -> new FamilyException(FamilyErrorResponse.FAMILY_NOT_FOUND))
                .toDomain();
    }
}
