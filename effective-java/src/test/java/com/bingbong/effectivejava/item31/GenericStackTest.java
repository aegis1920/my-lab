package com.bingbong.effectivejava.item31;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class GenericStackTest {

    @Test
    void pushAndPopTest() {
        GenericStack<Number> stack = new GenericStack<>();
        List<Integer> integers = Arrays.asList(1, 2, 3);

        // Number의 하위에 Integer가 있기때문에 가능!
        stack.pushAll(integers);
    }
}
