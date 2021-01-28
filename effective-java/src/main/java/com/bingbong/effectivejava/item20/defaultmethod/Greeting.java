package com.bingbong.effectivejava.item20.defaultmethod;

public interface Greeting {

    default String hello(String name) {
        return "Hello " + name + "!";
    }

    // Java 9에서는 private 메서드도 가능
//    private String defaultHello(String name) {
//        return "Hello " + name + "!";
//    }
}
