package com.bingbong.effectivejava.item20.templatemethodpattern;

public class PlusCalculator extends AbstractCalculator {

    public PlusCalculator(CalculateValidator calculateValidator) {
        super(calculateValidator);
    }

    @Override
    public boolean isSupport(CalculateType calculateType) {
        return calculateType == CalculateType.PLUS;
    }

    @Override
    protected int operate(int x, int y) {
        return x + y;
    }
}
