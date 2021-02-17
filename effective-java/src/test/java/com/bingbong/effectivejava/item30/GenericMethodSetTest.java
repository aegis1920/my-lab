package com.bingbong.effectivejava.item30;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GenericMethodSetTest {

    @Test
    void unionTest() {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        Set union = GenericMethodSet.union(set1, set2);
        Set<String> genericUnion = GenericMethodSet.genericUnion(set1, set2);
    }
}
