package com.bingbong.effectivejava.item29;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenericStackType2Test {

    @DisplayName("push와 pop에만 제네릭 사용")
    @Test
    void pushAndPopTest() {
        GenericStackType2<String> stack = new GenericStackType2<>();

//        stack.push(1); // 컴파일 에러!
        stack.push("1");

        // 서로 다른 클래스!
        assertThat(stack.pop()).isInstanceOf(String.class);
    }
}
