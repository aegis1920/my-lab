package com.bingbong.effectivejava.item32;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Picker {

    // Object[]를 반환한다.
    static <T> T[] toArray(T... args) {
        return args;
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }
        throw new AssertionError();
    }

    // 불변인 얘들
    static <T> List<T> safePickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return Arrays.asList(a, b);
            case 1: return Arrays.asList(a, c);
            case 2: return Arrays.asList(b, c);
        }
        throw new AssertionError();
    }
}
