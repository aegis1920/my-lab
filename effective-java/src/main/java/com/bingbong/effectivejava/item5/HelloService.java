package com.bingbong.effectivejava.item5;

public class HelloService {

    // final로 불변을 보장하자
    private final HelloRepository helloRepository;

    // 매개변수를 통해 의존객체를 받아 현재 객체를 생성하자
    // 매개변수를 통해 객체를 받을 수 있으므로 유연성, 테스트의 용이성, 재사용성을 증가시켜준다
    public HelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    // 매개변수를 통해 받지 않고, static 필드로 만든다던가, static으로 만들지 않되, 현재 객체를 싱글턴 객체로 만들면
    // 태스트하기 힘들어지고 공유되어 멀티스레드 환경에서 사용하기 힘들다
}
