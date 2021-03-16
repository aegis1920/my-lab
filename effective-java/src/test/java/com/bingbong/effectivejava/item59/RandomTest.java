package com.bingbong.effectivejava.item59;

import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomTest {

    @Test
    void randomTest() {
        Random random = new Random();
        int number = random.nextInt() % 100;
        int number2 = random.nextInt(100);

        System.out.println(number);
        System.out.println(number2);
    }

    @DisplayName("싱글 스레드에서 쓰이는 ThreadLocalRandom")
    @Test
    void threadLocalRandomTest() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int number = random.nextInt(100);

        System.out.println(number);
    }

    @DisplayName("병렬스트림에서 쓰이는 SplittableRandom")
    @Test
    void splittableRandomTest() {
        SplittableRandom random = new SplittableRandom();
        int number = random.nextInt(100);

        System.out.println(number);
    }
}
