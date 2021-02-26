package com.bingbong.effectivejava.item47;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StreamTest {

    @DisplayName("Stream에서 Iterable로 반환 - Stream에 명시적 형변환을 사용한 방법")
    @Test
    void streamToIterableByCast() {
        Stream<Integer> numberStream = Stream.of(1, 2, 3);

        for (int number : (Iterable<Integer>) numberStream::iterator) {
            assertThat(number <= 3).isTrue();
        }
    }

    @DisplayName("Stream에서 Iterable로 반환 - Stream에 어댑터 메서드를 사용한 방법")
    @Test
    void streamToIterableByAdapterMethod() {
        Stream<Integer> numberStream = Stream.of(1, 2, 3);

        for (int number : getIterable(numberStream)) {
            assertThat(number <= 3).isTrue();
        }
    }

    // 어댑터 메서드
    private static <T> Iterable<T> getIterable(Stream<T> stream) {
        return stream::iterator;
    }

    @DisplayName("Iterable에서 Stream로 반환 - Iterable에 어댑터 메서드를 사용한 방법")
    @Test
    void streamForEach3() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        Stream<Integer> numberStream = getStream(numbers);

        numberStream.forEach(number -> assertThat(number <= 3).isTrue());
    }

    // 어댑터 메서드
    private static <T> Stream<T> getStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

}
