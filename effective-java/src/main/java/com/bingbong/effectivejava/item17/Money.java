package com.bingbong.effectivejava.item17;

// 불변 클래스
// String, Wrapper Class, BigInteger, BigDecimal 등이 있다.
public class Money {

    private final int value;

    public Money(int value) {
        this.value = value;
    }

    public Money plus(int value) {
        return new Money(this.value + value);
    }

    public int getValue() {
        return value;
    }
}
