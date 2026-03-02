package com.v2.hyodoring.family.infrastructure.jpa.family.domain;

import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Getter
@Entity(name = "family")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FamilyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String name;

    @Column(unique = true, columnDefinition = "text", nullable = false)
    private String code;

    public static FamilyEntity from(Family family) {
        String familyName;
        if (StringUtils.hasText(family.getName())) {
            familyName = family.getName();
        } else {
            familyName = "행복한 가족";
        }
        return FamilyEntity.builder()
                .name(familyName)
                .code(family.getCode())
                .build();
    }

    public Family toDomain() {
        return Family.of(id, name, code, getCreatedAt(), getUpdatedAt());
    }
}
