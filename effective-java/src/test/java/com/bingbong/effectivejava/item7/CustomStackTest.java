package com.bingbong.effectivejava.item7;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.WeakHashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomStackTest {

    @DisplayName("null 처리를 하지 않고 pop하는 경우 데이터가 남아있음 - 메모리 누수의 원인")
    @Test
    void wrongPop() {
        CustomStack stack = new CustomStack();

        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        for (int i = 0; i < 10; i++) {
            stack.wrongPop();
        }

        for (int i = 0; i < 10; i++) {
            assertThat(stack.getElements()[i]).isNotNull();
        }
    }

    @DisplayName("null 처리를 함. 모두 null이 됨")
    @Test
    void pop() {
        CustomStack stack = new CustomStack();

        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        for (int i = 0; i < 10; i++) {
            stack.pop();
        }

        for (int i = 0; i < 10; i++) {
            assertThat(stack.getElements()[i]).isNull();
        }
    }

    @DisplayName("WeakHashMap 사용해서 GC되도록 하기")
    @Test
    void name() {
        Map<Integer, String> map = new WeakHashMap<>();

        Integer key1 = 127;
        Integer key2 = 128;

        map.put(key1, "hi 1");
        map.put(key2, "hi 2");

        key1 = null;
        key2 = null;

        System.gc();

        // 둘다 GC될 줄 알았으나 하나가 남아있는 이유는 Integer가 127까지 캐싱(static)을 해놓았기 때문.
        // 그렇기에 GC의 처리 대상이 아니다. 즉, 127은 강한 참조가 되어있다
        // 즉, 캐시를 하게 되면 GC의 처리대상이 아니게 되므로 조심하자.
        assertThat(map.entrySet().size()).isEqualTo(1);
    }
}
