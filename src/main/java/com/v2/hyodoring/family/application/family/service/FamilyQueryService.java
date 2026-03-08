package com.v2.hyodoring.family.application.family.service;

import com.v2.hyodoring.account.application.account.service.AccountQueryService;
import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.core.role.GranteeType;
import com.v2.hyodoring.account.infrastructure.jpa.role.repository.RoleQueryRepository;
import com.v2.hyodoring.family.application.family.domain.exception.FamilyErrorResponse;
import com.v2.hyodoring.family.application.family.domain.exception.FamilyException;
import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.core.family.FamilyMember;
import com.v2.hyodoring.family.core.family.FamilyRole;
import com.v2.hyodoring.family.infrastructure.jpa.family.domain.FamilyAccountEntity;
import com.v2.hyodoring.family.infrastructure.jpa.family.repository.FamilyAccountQueryRepository;
import com.v2.hyodoring.family.infrastructure.jpa.family.repository.FamilyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FamilyQueryService {
    private final FamilyQueryRepository familyQueryRepository;
    private final FamilyAccountQueryRepository familyAccountQueryRepository;
    private final RoleQueryRepository roleQueryRepository;

    private final AccountQueryService accountQueryService;

    // 가족 정보 조회
    public Family getFamilyInfo(Long familyId) {
        return familyQueryRepository.findById(familyId)
                .orElseThrow(() -> new FamilyException(FamilyErrorResponse.FAMILY_NOT_FOUND))
                .toDomain();
    }

    // 가족 구성원 정보 조회
    public List<FamilyMember> getAllFamilyMembers(Long familyId) {
        return familyAccountQueryRepository.findAllByFamilyId(familyId).stream()
                .filter(familyMemberEntity ->
                        accountQueryService.getAccount(familyMemberEntity.getAccountId()).isActive())
                .map(familyMemberEntity -> {
                    final Account account = accountQueryService.getAccount(familyMemberEntity.getAccountId());
                    final FamilyRole familyRole = roleQueryRepository
                            .findByAccountIdAndFamilyIdAndGranteeType(account.getId(), familyId, GranteeType.FAMILY)
                            .orElseThrow(() -> new FamilyException(FamilyErrorResponse.FAMILY_ROLE_NOT_FOUND))
                            .toFamilyRole();
                    return FamilyMember.of(
                            account.getId(),
                            account.getNickname(),
                            familyMemberEntity.getScore(),
                            familyRole.getName(),
                            familyMemberEntity.getCreatedAt()
                    );
                })
                .toList();
    }

    public boolean isFamilyMember(Long familyId, Long accountId) {
        final FamilyAccountEntity familyAccountEntity = familyAccountQueryRepository
                .findByFamilyIdAndAccountId(familyId, accountId).orElse(null);
        return familyAccountEntity != null;
    }

    public boolean existsByFamilyCode(String familyCode) {
        return familyQueryRepository.existsByCode(familyCode);
    }

    public Family findByFamilyCode(String familyCode) {
        return familyQueryRepository.findByCode(familyCode)
                .orElseThrow(() -> new FamilyException(FamilyErrorResponse.FAMILY_NOT_FOUND))
                .toDomain();
    }
}
