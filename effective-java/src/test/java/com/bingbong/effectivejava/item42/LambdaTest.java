package com.bingbong.effectivejava.item42;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LambdaTest {

    @DisplayName("익명 클래스로 정렬")
    @Test
    void anonymousClassTest() {
        List<Integer> numbers = Arrays.asList(4, 2, 5, 1, 3);
        numbers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        assertThat(numbers).containsExactly(1, 2, 3, 4, 5);
    }

    @DisplayName("람다식으로 정렬")
    @Test
    void lambdaTest() {
        List<Integer> numbers = Arrays.asList(4, 2, 5, 1, 3);
        numbers.sort((o1, o2) -> o1 - o2);

        assertThat(numbers).containsExactly(1, 2, 3, 4, 5);
    }

    @DisplayName("Comparator가 지원하는 ToIntFunction 표준 함수형 인터페이스로 정렬")
    @Test
    void comparingIntTest() {
        List<Integer> numbers = Arrays.asList(4, 2, 5, 1, 3);
        numbers.sort(Comparator.comparingInt(o -> o));

        assertThat(numbers).containsExactly(1, 2, 3, 4, 5);
    }

    @DisplayName("로 타입 람다식 정렬 - 컴파일 에러")
    @Test
    void RawTypeLambdaTest() {
        List numbers = Arrays.asList(4, 2, 5, 1, 3);
//        numbers.sort((o1, o2) -> o1 - o2);

        assertThat(numbers).containsExactly(4, 2, 5, 1, 3);
    }

    @DisplayName("익명 클래스에 선언된 this는 익명 클래스의 인스턴스를 가리킨다")
    @Test
    void anonymousClassThisTest() {
        Lambda lambda = new Lambda();

        assertThat(lambda.anonymousClassInstance.addWithThisValue(10)).isEqualTo(30);
    }

    @DisplayName("람다식에 선언된 this는 바깥 클래스의 인스턴스를 가리킨다")
    @Test
    void lambdaThisTest() {
        Lambda lambdaThis = new Lambda();

        assertThat(lambdaThis.FunctionalInterfaceInstance.addWithThisValue(10)).isEqualTo(20);
    }
}
