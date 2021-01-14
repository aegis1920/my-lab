package com.bingbong.effectivejava.item4;

public class CustomStringUtils {

    // Exception을 던지지 않고 주석을 사용할 수도 있다.
    // private CustomStringUtils() {}

    private CustomStringUtils() {
        throw new IllegalStateException("유틸리티 클래스를 인스턴스화할 수 없습니다!");
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
}
