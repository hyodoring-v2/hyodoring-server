package com.v2.hyodoring.account.infrastructure.jpa.role.domain;

import com.v2.hyodoring.account.core.account.domain.AccountRole;
import com.v2.hyodoring.account.core.role.AccountRoleType;
import com.v2.hyodoring.account.core.role.GranteeType;
import com.v2.hyodoring.account.infrastructure.jpa.base.domain.BaseEntity;
import com.v2.hyodoring.family.core.family.FamilyRole;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "role")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_role_grantee_id_grantee_type", columnNames = {"account_id", "family_id", "grantee_type"})
        }
)
@Builder(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column
    private Long familyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GranteeType granteeType;

    @Column(columnDefinition = "text", nullable = false)
    private String name;

    public static RoleEntity from(AccountRole accountRole) {
        return RoleEntity.builder()
                .accountId(accountRole.getAccountId())
                .familyId(0L)
                .granteeType(GranteeType.ACCOUNT)
                .name(accountRole.getName().toString())
                .build();
    }

    public static RoleEntity from(FamilyRole familyRole) {
        return RoleEntity.builder()
                .accountId(familyRole.getAccountId())
                .familyId(familyRole.getFamilyId())
                .granteeType(GranteeType.FAMILY)
                .name(familyRole.getName().toString())
                .build();
    }

    public FamilyRole toFamilyRole() {
        if (!GranteeType.FAMILY.equals(granteeType)) {
            throw new IllegalArgumentException("granteeType must be family");
        }
        return FamilyRole.of(id, accountId, familyId, FamilyRoleType.valueOf(name), getCreatedAt(), getUpdatedAt());
    }

    public AccountRole toAccountRole() {
        if (!GranteeType.ACCOUNT.equals(granteeType)) {
            throw new IllegalArgumentException("granteeType must be account");
        }
        return AccountRole.of(id, accountId, AccountRoleType.valueOf(name), getCreatedAt(), getUpdatedAt());
    }
}
