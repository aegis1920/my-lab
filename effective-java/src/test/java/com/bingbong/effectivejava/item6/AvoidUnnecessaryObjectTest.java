package com.bingbong.effectivejava.item6;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class AvoidUnnecessaryObjectTest {

    @Test
    void stringPoolTest() {
        String s1 = new String("hello world");
        String s2 = new String("hello world");
        String s3 = "hello world";
        String s4 = "hello world";

        assertThat(s1 == s2).isFalse(); // 새로운 객체 생성
        assertThat(s3 == s4).isTrue(); // 상수 풀
    }

    @Test
    void integerTest() {
        Integer i1 = new Integer(50);
        Integer i2 = new Integer(50);
        Integer i3 = Integer.valueOf(50);
        Integer i4 = Integer.valueOf(50);
        Integer i5 = 50;

        assertThat(i1 == i2).isFalse();
        assertThat(i3 == i4).isTrue();
        assertThat(i3 == i5).isTrue();
    }

    @Test
    void patternTest() {
        // 이런 Pattern 객체나 Connection 객체, IO 작업 Object는 인스턴스 생성 비용이 비싸다
        Pattern pattern = Pattern.compile("\\d");

        assertThat(pattern.matcher("3").matches()).isTrue();
    }

    @Test
    void autoBoxingTest() {
        long result = 0;
        long start = System.currentTimeMillis();

        for (int i = 0 ; i < Integer.MAX_VALUE ; i++) {
            result += Integer.valueOf(i);
        }

        long end = System.currentTimeMillis();

        long boxingTime = end - start;

        result = 0;
        start = System.currentTimeMillis();

        for (int i = 0 ; i < Integer.MAX_VALUE ; i++) {
            result += i;
        }

        end = System.currentTimeMillis();

        long notBoxingTime = end - start;
        System.out.println("notBoxingTime : " + notBoxingTime);
        System.out.println("boxingTime : " + boxingTime);

        // 거의 10배정도 차이가 난다. 최대한 기본 타입으로 사용하자.
        assertThat(boxingTime).isGreaterThan(notBoxingTime);
    }
}
