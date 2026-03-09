package com.v2.hyodoring.family.core.role;

import lombok.Getter;

@Getter
public enum FamilyRoleType {
    MOM("어머니"),
    DAD("아버지"),
    SON("아들"),
    DAUGHTER("딸"),
    ;

    private final String label;

    FamilyRoleType(String label) {
        this.label = label;
    }
}
