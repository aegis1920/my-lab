package com.bingbong.effectivejava.item3;

public class StaticFactoryMethodSingleton {

    // 정적팩토리메서드를 통한 싱글턴 방식
    // 싱글턴은 전역성을 가져서 공유가 가능해지 메모리 낭비를 방지할 수 있다. 그러나 Mock으로 대체할 수 없어 테스트하기 어렵다.
    // 게다가 직렬화하기 어렵다. Serilaizable을 사용하면 같은 객체로 참조하지 못한다.
    private static final StaticFactoryMethodSingleton INSTANCE = new StaticFactoryMethodSingleton();

    private StaticFactoryMethodSingleton() {}

    public static StaticFactoryMethodSingleton getInstance() {
        return INSTANCE;
    }
}
