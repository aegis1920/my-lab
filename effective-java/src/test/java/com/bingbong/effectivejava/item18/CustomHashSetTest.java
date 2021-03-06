package com.bingbong.effectivejava.item18;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomHashSetTest {

    @DisplayName("더할 때마다 카운트 증가 - 상속")
    @Test
    void addAll_Inheritance() {
        CustomHashSetByInheritance<Integer> customHashSet = new CustomHashSetByInheritance<>();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        customHashSet.addAll(numbers);
        customHashSet.add(5);

        assertThat(customHashSet.getAddCount()).isNotEqualTo(5);
        assertThat(customHashSet.getAddCount()).isEqualTo(9);
    }

    @DisplayName("더할 때마다 카운트 증가 - 컴포지션")
    @Test
    void addAll_Composition() {
        CustomHashSetByComposition<Integer> customHashSet = new CustomHashSetByComposition<>(
            new HashSet<>());
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        customHashSet.addAll(numbers);
        customHashSet.add(5);

        assertThat(customHashSet.getAddCount()).isEqualTo(5);
    }
}
