package com.bingbong.effectivejava.item20.templatemethodpattern;

public class MinusCalculator extends AbstractCalculator {

    public MinusCalculator(CalculateValidator calculateValidator) {
        super(calculateValidator);
    }

    @Override
    public boolean isSupport(CalculateType calculateType) {
        return calculateType == CalculateType.MINUS;
    }

    @Override
    protected int operate(int x, int y) {
        return x - y;
    }
}
