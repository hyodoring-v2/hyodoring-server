package com.v2.hyodoring.family.application.family.service;

import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.infrastructure.jpa.role.domain.RoleEntity;
import com.v2.hyodoring.account.infrastructure.jpa.role.repository.RoleCommandRepository;
import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.core.family.FamilyAccount;
import com.v2.hyodoring.family.core.family.FamilyRole;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import com.v2.hyodoring.family.infrastructure.jpa.family.domain.FamilyAccountEntity;
import com.v2.hyodoring.family.infrastructure.jpa.family.domain.FamilyEntity;
import com.v2.hyodoring.family.infrastructure.jpa.family.repository.FamilyAccountCommandRepository;
import com.v2.hyodoring.family.infrastructure.jpa.family.repository.FamilyCommandRepository;
import com.v2.hyodoring.family.infrastructure.jpa.family.repository.FamilyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyCommandService {

    private final FamilyCommandRepository familyCommandRepository;
    private final FamilyQueryRepository familyQueryRepository;
    private final FamilyAccountCommandRepository familyAccountCommandRepository;

    private final RoleCommandRepository roleCommandRepository;

    /**
     * 가족을 생성하는 메서드
     * 가족 생성과 동시에 사용자를 가족 구성원으로 등록한다.
     * @param family 생성할 가족 정보
     * @param account 가족 구성원 정보
     * @return {@link Family}
     */
    public Family generateFamily(Family family, FamilyRoleType role, Account account) {
        final FamilyEntity familyEntity = familyCommandRepository.save(FamilyEntity.from(family));
        roleCommandRepository.save(RoleEntity.from(
                FamilyRole.create(account.getId(), familyEntity.getId(), role)));
        familyAccountCommandRepository.save(FamilyAccountEntity
                .from(FamilyAccount.create(
                        familyEntity.getId(),
                        account.getId()
                )));
        return familyEntity.toDomain();
    }

    /**
     * 가족에 참여하는 메서드
     * 가족에 참여함과 동시에 사용자를 가족 구성원으로 등록한다.
     * @param familyRole 구성원 역할 정보
     * @param account 가족 구성원 정보
     * @return {@link FamilyRole}
     */
    public FamilyRole joinFamily(FamilyRole familyRole, Account account) {
        final RoleEntity roleEntity = roleCommandRepository.save(RoleEntity.from(familyRole));
        familyAccountCommandRepository.save(FamilyAccountEntity
                .from(FamilyAccount.create(
                        familyRole.getFamilyId(),
                        account.getId()
                )));
        return roleEntity.toFamilyRole();
    }
}
