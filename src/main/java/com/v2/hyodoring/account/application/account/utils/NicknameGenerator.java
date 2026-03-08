package com.v2.hyodoring.account.application.account.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class NicknameGenerator {

    private static final String CHARACTER_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final List<String> ADJECTIVES = List.of(
            "행복한", "즐거운", "든든한", "효도하는", "당당한", "귀여운", "유쾌한"
    );

    private static final List<String> NOUNS = List.of(
            "호랑이", "사자", "거북이", "토끼", "강아지", "고양이", "공룡", "사슴", "치타"
    );

    public static String generateNickname() {
        String adjective = ADJECTIVES.get(RANDOM.nextInt(ADJECTIVES.size()));
        String noun = NOUNS.get(RANDOM.nextInt(NOUNS.size()));
        String id = IntStream.range(0, CODE_LENGTH)
                .map(i -> RANDOM.nextInt(CHARACTER_POOL.length()))
                .mapToObj(CHARACTER_POOL::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        return String.format("%s%s#%d", adjective, noun, id);
    }
}
