package com.bingbong.effectivejava.item14;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ComparableTest {

    @Test
    void comparable() {
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

    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student o) {
        return Integer.compare(this.score, o.score);
    }
}
