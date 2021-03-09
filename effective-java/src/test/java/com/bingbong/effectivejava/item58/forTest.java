package com.bingbong.effectivejava.item58;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class forTest {


    @DisplayName("전통적인 for문, 반복자로 컬렉션 순회")
    @Test
    void originForByIterator() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> resultNumbers = new ArrayList<>();

        for (Iterator<Integer> i = numbers.iterator(); i.hasNext(); ) {
            resultNumbers.add(i.next());
        }

        assertThat(resultNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("배열로 컬렉션 순회")
    @Test
    void originForByArray() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int[] resultNumbers = new int[numbers.size()];

        for (int i = 0; i < resultNumbers.length; i++) {
            resultNumbers[i] = numbers.get(i);
        }

        assertThat(resultNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("for-each(향상된 For문)으로 컬렉션 순회")
    @Test
    void enhancedForEachByCollection() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> resultNumbers = new ArrayList<>();

        for (Integer number : numbers) {
            resultNumbers.add(number);
        }

        assertThat(resultNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("iterable For문으로 컬렉션 순회")
    @Test
    void iterableForEachByCollection() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> resultNumbers = new ArrayList<>();

        numbers.forEach(resultNumbers::add);

        assertThat(resultNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("stream For문으로 컬렉션 순회")
    @Test
    void streamForEachByCollection() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> resultNumbers = new ArrayList<>();

        numbers.stream().forEach(resultNumbers::add);

        assertThat(resultNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("iterator의 next() 잘못 사용 시")
    @Test
    void iteratorNext_fail() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        int count = 0;

        for (Iterator<Integer> i = numbers.iterator(); i.hasNext(); ) {
            for (Iterator<Integer> j = numbers.iterator(); j.hasNext(); ) {
                i.next();
                j.next();
                count++;
            }
        }

        assertThat(count).isNotEqualTo(36);
        assertThat(count).isEqualTo(6);
    }

    @DisplayName("iterator의 next()를 제대로 사용 시")
    @Test
    void iteratorNext_success() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int count = 0;

        for (Iterator<Integer> i = numbers.iterator(); i.hasNext(); i.next()) {
            for (Iterator<Integer> j = numbers.iterator(); j.hasNext(); j.next()) {
                count++;
            }
        }

        assertThat(count).isEqualTo(36);
    }

    @DisplayName("향상된 for문 사용 시")
    @Test
    void enhancedForEachCount() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int count = 0;

        for (Integer number : numbers) {
            for (Integer number2 : numbers) {
                count++;
            }
        }

        assertThat(count).isEqualTo(36);
    }

    @DisplayName("향상된 for문에서 remove 사용 시 예외 발생")
    @Test
    void enhancedForEachRemove() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> {
            for (Integer number : numbers) {
                if (number.equals(3)) {
                    numbers.remove((Integer) 3);
                }
            }
        }).isInstanceOf(ConcurrentModificationException.class);
    }

    @DisplayName("iterator For문과 enhance ForEach 성능 비교")
    @Test
    void name8() {
        List<Integer> numbers = IntStream.rangeClosed(1, 10000000)
            .boxed()
            .collect(Collectors.toList());

        double iteratorTime = calculateMethodTime(() -> iteratorFor(numbers));
        double forInTime = calculateMethodTime(() -> enhanceForEach(numbers));

        assertThat(forInTime).isGreaterThan(iteratorTime);
    }

    private double calculateMethodTime(TimeFunction method) {
        long start = System.currentTimeMillis();
        method.execute();
        long end = System.currentTimeMillis();

        return (end - start) / 1000.0;
    }

    private void iteratorFor(List<Integer> numbers) {
        for (Iterator<Integer> i = numbers.iterator(); i.hasNext(); i.next()) {
        }
    }

    private void enhanceForEach(List<Integer> numbers) {
        for (Integer number : numbers) {
        }
    }
}
