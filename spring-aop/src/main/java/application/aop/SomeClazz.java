package application.aop;

import org.springframework.stereotype.Component;

@Component
public class SomeClazz {

    public String someMethod() {
        return "someMethod를 실행하셨습니다.";
    }
}
