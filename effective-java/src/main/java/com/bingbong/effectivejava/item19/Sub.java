package com.bingbong.effectivejava.item19;

public class Sub extends Super {

    private final String value;

    Sub() {
        value = "Hello World!";
    }

    @Override
    public void overrideMe() {
        System.out.println(value);
    }
}
