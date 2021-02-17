package com.bingbong.effectivejava.item29;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenericStackType1Test {

    @DisplayName("제네릭 배열을 사용")
    @Test
    void pushAndPopTest() {
        GenericStackType1<String> stack = new GenericStackType1<>();

//        stack.push(1); // 컴파일 시점에 에러!
        stack.push("1"); // Object라서 다 들어간다

        // 서로 다른 클래스!
        assertThat(stack.pop()).isInstanceOf(String.class);
    }
}
