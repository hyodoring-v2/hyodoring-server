package com.v2.hyodoring.family.core.family;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Family {
    private final Long id;
    private final String name;
    private final String code;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Family(Long id, String name, String code, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Family create(String code) {
        return new Family(null, null, code, null, null);
    }
    public static Family create(String name, String code) {
        return new Family(null, name, code, null, null);
    }

    public static Family of(Long id, String name, String code, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Family(id, name, code, createdAt, updatedAt);
    }
}
