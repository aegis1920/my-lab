package com.bingbong.effectivejava.item42;

public class Lambda {

    private final int value = 10;

    public LambdaInterface anonymousClassInstance = new LambdaInterface() {
        final int value = 20;
        @Override
        public int addWithThisValue(int i) {
            return i + this.value;
        }
    };

    public LambdaInterface FunctionalInterfaceInstance = i -> i + this.value;
}
