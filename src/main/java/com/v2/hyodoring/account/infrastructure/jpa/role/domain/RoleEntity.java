package com.v2.hyodoring.account.infrastructure.jpa.role.domain;

import com.v2.hyodoring.account.core.role.GranteeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity(name = "role")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_role_grantee_id_grantee_type", columnNames = {"grantee_id", "grantee_type"})
        }
)
@Builder(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long granteeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GranteeType granteeType;

    @Column(columnDefinition = "text", nullable = false)
    private String name;

    public static RoleEntity of(long granteeId, GranteeType granteeType, String name) {
        Assert.notNull(granteeType, "granteeType can not be null");
        Assert.hasText(name, "name must not be empty");
        return RoleEntity.builder()
                .granteeId(granteeId)
                .granteeType(granteeType)
                .name(name)
                .build();
    }
}
