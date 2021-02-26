package com.bingbong.effectivejava.item46;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollectorsTest {

    private List<String> names;

    @BeforeEach
    void setUp() {
        names = new ArrayList<>(Arrays.asList("a", "bb", "a", "ccc", "dddd"));
    }

    @Test
    void toListTest() {
        List<String> collectedNames = names.stream().collect(Collectors.toList());

        assertThat(collectedNames).containsExactly("a", "bb", "a", "ccc", "dddd");
    }

    @Test
    void toSetTest() {
        Set<String> collectedNames = names.stream().collect(Collectors.toSet());

        assertThat(collectedNames).containsExactly("bb", "a", "ccc", "dddd");
    }

    @Test
    void toCollectionTest() {
        // 수집 후 직접 구현체를 정해줄 수 있다
        List<String> collectedNames = names.stream().collect(Collectors.toCollection(ArrayList::new));

        assertThat(collectedNames).containsExactly("a", "bb", "a", "ccc", "dddd");
    }
}
