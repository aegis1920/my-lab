package com.bingbong.effectivejava.item52;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OverloadingTest {

    @DisplayName("list에서 3번째 인덱스를 삭제")
    @Test
    void removeOverloadingTest() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        list.remove(3);

        assertThat(list).containsExactly(1, 2, 3, 5);
    }

    @DisplayName("list에서 박싱한 3을 삭제")
    @Test
    void removeOverloadingBoxingTest() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        list.remove((Integer) 3);

        assertThat(list).containsExactly(1, 2, 4, 5);
    }
}
