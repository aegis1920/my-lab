package com.bingbong.effectivejava.item20.templatemethodpattern;

public class CalculateValidator {

    public void validate(int x, int y) {
        validateNumber(x);
        validateNumber(y);
    }

    private void validateNumber(int number) {
        if (number < 0 || number > 100) {
            throw new IllegalArgumentException("0이상 100이하");
        }
    }
}
