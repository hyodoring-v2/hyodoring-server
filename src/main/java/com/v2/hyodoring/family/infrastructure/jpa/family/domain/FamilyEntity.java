package com.v2.hyodoring.family.infrastructure.jpa.family.domain;

import com.v2.hyodoring.family.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

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

    @Column(columnDefinition = "text", nullable = false)
    private String code;

    public static FamilyEntity of(String name, String code) {
        Assert.hasText(name, "name must not be empty");
        Assert.hasText(code, "code must not be empty");
        return FamilyEntity.builder()
                .name(name)
                .code(code)
                .build();
    }
}
