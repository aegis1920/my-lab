package com.bingbong.effectivejava.item20.templatemethodpattern;

public abstract class AbstractCalculator implements Calculator {

    private final CalculateValidator calculateValidator;

    protected AbstractCalculator(
        CalculateValidator calculateValidator) {
        this.calculateValidator = calculateValidator;
    }

    @Override
    public int calculate(int x, int y) {
        calculateValidator.validate(x, y);

        return operate(x, y);
    }

    public abstract boolean isSupport(CalculateType calculateType);

    protected abstract int operate(int x, int y);
}
