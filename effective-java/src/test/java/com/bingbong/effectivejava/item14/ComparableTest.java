package com.bingbong.effectivejava.item14;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ComparableTest {

    @DisplayName("Comparable이 이미 되어있는 클래스 정렬")
    @Test
    void comparableExampleTest() {
        // String과 Integer 클래스에 Comparable이 구현되어있음
        List<String> names = Arrays.asList("b", "c", "a");
        List<Integer> numbers = Arrays.asList(2, 3, 1);

        List<String> sortedNames = names.stream()
            .sorted()
            .collect(Collectors.toList());

        List<Integer> sortedNumbers = numbers.stream()
            .sorted()
            .collect(Collectors.toList());

        assertThat(sortedNames.get(0)).isEqualTo("a");
        assertThat(sortedNames.get(1)).isEqualTo("b");
        assertThat(sortedNames.get(2)).isEqualTo("c");

        assertThat(sortedNumbers.get(0)).isEqualTo(1);
        assertThat(sortedNumbers.get(1)).isEqualTo(2);
        assertThat(sortedNumbers.get(2)).isEqualTo(3);
    }

    @DisplayName("BigDecimal을 HashSet과 TreeSet에 넣었을 때 차이")
    @Test
    void hashSetIsEqualsAndTreeSetIsCompareToTest() {
        BigDecimal number1 = new BigDecimal("1.0");
        BigDecimal number2 = new BigDecimal("1.00");

        // HashSet은 equals로 판단하기 때문에 다름
        Set<BigDecimal> hashSet = new HashSet<>();
        // TreeSet은 CompareTo로 판단하기 때문에 같음
        Set<BigDecimal> treeSet = new TreeSet<>();

        hashSet.add(number1);
        hashSet.add(number2);

        treeSet.add(number1);
        treeSet.add(number2);

        assertThat(hashSet.size()).isEqualTo(2);
        assertThat(treeSet.size()).isEqualTo(1);
    }

    @DisplayName("Overflow되는 int 정렬")
    @Test
    void overflowComparableTest() {
        List<Integer> numbers = Arrays.asList(Integer.MAX_VALUE, -1);

        // 내림차순으로 정렬
        numbers.sort((o1, o2) -> o2 - o1);

        // 2147483647) - (-1) = -2147483648. 양수가 나와야하는데 overflow로 음수가 나오기 때문에 잘못정렬됨
        assertThat(Integer.MAX_VALUE - (-1)).isEqualTo(Integer.MIN_VALUE);

        assertThat(numbers.get(0)).isEqualTo(-1);
        assertThat(numbers.get(1)).isEqualTo(Integer.MAX_VALUE);
    }

    @DisplayName("Not Overflow되는 int 정렬")
    @Test
    void notOverflowComparableTest() {
        List<Integer> numbers = Arrays.asList(3, -1);

        // 내림차순으로 정렬
        numbers.sort((o1, o2) -> o2 - o1);

        // 3 - (-1) = 4 제대로 된 양수가 나옴
        assertThat(numbers.get(0)).isEqualTo(3);
        assertThat(numbers.get(1)).isEqualTo(-1);
    }

    @DisplayName("Overflow compareTo 정렬")
    @Test
    void compareToComparableTest() {
        List<Integer> numbers = Arrays.asList(Integer.MAX_VALUE, -1);

        // 내림차순으로 정렬
        // Overflow되지만 compareTo로 하면 크기로 비교하기 때문에 제대로 정렬
        numbers.sort((o1, o2) -> o2.compareTo(o1));

        assertThat(numbers.get(0)).isEqualTo(Integer.MAX_VALUE);
        assertThat(numbers.get(1)).isEqualTo(-1);
    }

    @DisplayName("커스텀 Comparable 클래스 정렬")
    @Test
    void customComparableTest() {
        List<Student> students = Arrays.asList(
            new Student("bingbong1", 3),
            new Student("bingbong2", 1),
            new Student("bingbong3", 2)
        );

        List<Student> collect = students.stream()
            .sorted()
            .collect(Collectors.toList());

        assertThat(collect.get(0).score).isEqualTo(1);
        assertThat(collect.get(1).score).isEqualTo(2);
        assertThat(collect.get(2).score).isEqualTo(3);
    }
}

class Student implements Comparable<Student> {

    // 기준을 이렇게 적어놓고 사용하면 훨씬 가독성있다
    private static final Comparator<Student> STANDARD_COMPARATOR =
        Comparator.comparingInt((Student student) -> student.score)
            .thenComparing((Student student) -> student.name);

    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student o) {
        return STANDARD_COMPARATOR.compare(this, o);
    }

    //    @Override
//    public int compareTo(Student o) {
//        return Integer.compare(this.score, o.score);
//    }
}


