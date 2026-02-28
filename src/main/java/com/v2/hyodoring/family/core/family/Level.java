package com.v2.hyodoring.family.core.family;

import java.util.TreeMap;

public enum Level {
    STRANGER,       // Lv1. 남보다 못한 사이
    AWKWARD,        // Lv2. 조금 어색한 사이
    BEGINNER,       // Lv3. 효도 초보자
    TRAINEE,        // Lv4. 효도 견습생
    NORMAL,         // Lv5. 평범한 사이
    PRIDE,          // Lv6. 가족의 자랑
    EXPERT,         // Lv7. 효도 전문가
    MASTER,         // Lv8. 효도 마스터
    LEGEND,         // Lv9. 전설의 효자
    GOD_OF_HYODO,   // Lv10. 효도의 신
    ;

    private static final TreeMap<Integer, Level> LEVEL_MAP = new TreeMap<>();

    static {
        LEVEL_MAP.put(0, STRANGER);
        LEVEL_MAP.put(10, AWKWARD);
        LEVEL_MAP.put(20, BEGINNER);
        LEVEL_MAP.put(30, TRAINEE);
        LEVEL_MAP.put(40, NORMAL);
        LEVEL_MAP.put(50, PRIDE);
        LEVEL_MAP.put(60, EXPERT);
        LEVEL_MAP.put(70, MASTER);
        LEVEL_MAP.put(80, LEGEND);
        LEVEL_MAP.put(90, GOD_OF_HYODO);
    }

    public static Level fromScore(int score) {
        return LEVEL_MAP.floorEntry(score).getValue();
    }
}
