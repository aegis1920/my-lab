package com.bingbong.effectivejava.item20.templatemethodpattern;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @DisplayName("덧셈")
    @Test
    void plusCalculatorTest() {
        PlusCalculator plusCalculator = new PlusCalculator(new CalculateValidator());

        assertThat(plusCalculator.operate(1, 2)).isEqualTo(3);
    }

    @DisplayName("뺄셈")
    @Test
    void minusCalculatorTest() {
        MinusCalculator minusCalculator = new MinusCalculator(new CalculateValidator());

        assertThat(minusCalculator.operate(1, 2)).isEqualTo(-1);
    }
}
