package com.bingbong.effectivejava.item24;

import java.util.function.BinaryOperator;

public class Calculator {

    public enum Operation {
        PLUS(Integer::sum),
        MINUS((x, y) -> x - y);

        private final BinaryOperator<Integer> calculate;

        Operation(BinaryOperator<Integer> calculate) {
            this.calculate = calculate;
        }
    }

    public int sum(int x, int y) {
        return Operation.PLUS.calculate.apply(x, y);
    }
}
