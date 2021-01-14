package com.bingbong.effectivejava.item1;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class StaticFactoryMethodTest {

    @Test
    void unmodifiableList() {
        // 반환 타입의 하위 타입 객체를 반환할 수 있다.
        // 매개변수에 따라 다른 클래스의 객체를 반환할 수 있다.
        Collections.unmodifiableList(new ArrayList<>());
    }
}
