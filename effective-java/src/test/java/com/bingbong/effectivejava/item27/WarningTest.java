package com.bingbong.effectivejava.item27;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WarningTest {

    @DisplayName("타입을 정해주지 않아 경고")
    @Test
    void uncheckedCall() {
        Set words = new HashSet();
        words.add("hello");
        words.add(1);

        assertThat(words.contains("hello")).isTrue();
        assertThat(words.contains(1)).isTrue();
    }

    @DisplayName("int를 String으로 받으려 해서 ClassCastException. heap Pollution")
    @Test
    void heapPollution() {
        List list = toList("1", 2);

        Iterator<String> iter = list.iterator();

        assertThatThrownBy(() -> {
            while (iter.hasNext()) {
                String str = iter.next(); // ClassCastException
            }
        }).isInstanceOf(ClassCastException.class);

    }

    private <T> List<T> toList(T... elements) {
        return Arrays.asList(elements);
    }

    @SuppressWarnings("unchecked")
    class ClassWarning {

        public Set<String> hello() {
            return new HashSet();
        }
    }

    class MethodWarning {

        public Set<String> hello() {
            return new HashSet();
        }
    }

    class FieldWarning {

        @SuppressWarnings("unchecked")
        Set<String> set = new HashSet();
    }
}
