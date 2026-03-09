package com.v2.hyodoring.family.application.family.service;

import com.v2.hyodoring.account.application.account.service.AccountQueryService;
import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.core.role.GranteeType;
import com.v2.hyodoring.account.infrastructure.jpa.role.repository.RoleQueryRepository;
import com.v2.hyodoring.family.application.family.domain.exception.FamilyErrorResponse;
import com.v2.hyodoring.family.application.family.domain.exception.FamilyException;
import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.core.family.FamilyAccount;
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


    /**
     * 가족 기본 정보 조회 메서드
     * @param familyId 가족 id
     * @return 가족 정보 (가족 id, 이름, 코드, 생성일, 수정일)
     */
    public Family getFamilyInfo(Long familyId) {
        return familyQueryRepository.findById(familyId)
                .orElseThrow(() -> new FamilyException(FamilyErrorResponse.FAMILY_NOT_FOUND))
                .toDomain();
    }

    /**
     * 가족 구성원 조회 메서드
     * @param familyId 가족 id
     * @param accountId 계정 id
     * @return 가족 구성원 정보 (계정 id, 닉네임, 레벨, 역할, 가족 참여일)
     */
    public FamilyMember getFamilyMember(Long familyId, Long accountId) {
        if (!familyQueryRepository.existsById(familyId)) {
            throw new FamilyException(FamilyErrorResponse.FAMILY_NOT_FOUND);
        }
        // 가족 구성원 조회
        final FamilyAccount familyAccount = familyAccountQueryRepository
                .findByFamilyIdAndAccountId(familyId, accountId)
                .orElseThrow(() -> new FamilyException(FamilyErrorResponse.FAMILY_MEMBER_NOT_FOUND))
                .toDomain();
        return getFamilyMemberInfo(familyAccount);
    }

    /**
     * 가족 내 모든 구성원 조회 메서드
     * @param familyId 가족 id
     * @return 가족 내 모든 구성원 정보 (계정 id, 닉네임, 레벨, 역할, 가족 참여일) 리스트
     */
    public List<FamilyMember> getAllFamilyMembers(Long familyId) {
        if (!familyQueryRepository.existsById(familyId)) {
            throw new FamilyException(FamilyErrorResponse.FAMILY_NOT_FOUND);
        }
        // 모든 가족 구성원 조회
        return familyAccountQueryRepository.findAllByFamilyId(familyId).stream()
                .filter(familyMemberEntity ->
                        accountQueryService.getAccount(familyMemberEntity.getAccountId()).isActive())
                .map(familyMemberEntity -> getFamilyMemberInfo(familyMemberEntity.toDomain()))
                .toList();
    }

    /**
     * 특정 계정이 특정 가족의 구성원인지 확인하는 메서드
     * @param familyId 가족 id
     * @param accountId 계정 id
     * @return 가족 구성원이면 true, 아니면 false
     */
    public boolean isFamilyMember(Long familyId, Long accountId) {
        final FamilyAccountEntity familyAccountEntity = familyAccountQueryRepository
                .findByFamilyIdAndAccountId(familyId, accountId).orElse(null);
        return familyAccountEntity != null;
    }

    /**
     * 가족 코드를 통해 존재하는 가족인지 확인하는 메서드
     * @param familyCode 가족 코드
     * @return 존재하는 가족이면 true, 아니면 false
     */
    public boolean existsByFamilyCode(String familyCode) {
        return familyQueryRepository.existsByCode(familyCode);
    }

    /**
     * 가족 코드를 통해 가족 정보를 조회하는 메서드
     * @param familyCode 가족 코드
     * @return 가족 정보 (가족 id, 이름, 코드, 생성일, 수정일)
     */
    public Family findByFamilyCode(String familyCode) {
        return familyQueryRepository.findByCode(familyCode)
                .orElseThrow(() -> new FamilyException(FamilyErrorResponse.FAMILY_NOT_FOUND))
                .toDomain();
    }

    /**
     * FamilyAccount, Account, FamilyRole 정보를 통해 FamilyMember 객체를 생성하는 메서드
     * @param familyAccount {@link FamilyAccount} 객체
     * @return 가족 구성원 정보 (계정 id, 닉네임, 레벨, 역할, 가족 참여일)
     */
    private FamilyMember getFamilyMemberInfo(FamilyAccount familyAccount) {
        final Account account = accountQueryService.getAccount(familyAccount.getAccountId());
        final FamilyRole familyRole = roleQueryRepository
                .findByAccountIdAndFamilyIdAndGranteeType(account.getId(), familyAccount.getFamilyId(), GranteeType.FAMILY)
                .orElseThrow(() -> new FamilyException(FamilyErrorResponse.FAMILY_ROLE_NOT_FOUND))
                .toFamilyRole();
        return FamilyMember.of(
                account.getId(),
                account.getNickname(),
                familyAccount.getScore(),
                familyRole.getName(),
                familyAccount.getCreatedAt()
        );
    }
}
