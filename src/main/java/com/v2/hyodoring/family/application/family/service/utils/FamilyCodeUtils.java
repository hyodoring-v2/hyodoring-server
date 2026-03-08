package com.v2.hyodoring.family.application.family.service.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class FamilyCodeUtils {

    private static final String CHARACTER_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 6자리의 영문 대문자 및 숫자로 구성된 가족 코드를 생성하는 메서드
     * @return 생성된 가족 코드
     */
    public static String generateFamilyCode() {
        return IntStream.range(0, CODE_LENGTH)
                .map(i -> RANDOM.nextInt(CHARACTER_POOL.length()))
                .mapToObj(CHARACTER_POOL::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
