package com.v2.hyodoring.family.infrastructure.jpa.family.domain;

import com.v2.hyodoring.family.core.family.FamilyAccount;
import com.v2.hyodoring.family.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity(name = "family_account")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"family_id", "account_id"}))
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FamilyAccountEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long familyId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Integer score;

    public static FamilyAccountEntity from(FamilyAccount familyAccount) {
        return FamilyAccountEntity.builder()
                .familyId(familyAccount.getFamilyId())
                .accountId(familyAccount.getAccountId())
                .score(familyAccount.getScore())
                .build();
    }

    public FamilyAccount toDomain() {
        return FamilyAccount.of(id, familyId, accountId, score, getCreatedAt(), getUpdatedAt());
    }
}
