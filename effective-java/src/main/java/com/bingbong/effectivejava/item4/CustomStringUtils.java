package com.bingbong.effectivejava.item4;

public class CustomStringUtils {

    private CustomStringUtils() {
        throw new IllegalStateException("유틸리티 클래스를 인스턴스화할 수 없습니다!");
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
}
