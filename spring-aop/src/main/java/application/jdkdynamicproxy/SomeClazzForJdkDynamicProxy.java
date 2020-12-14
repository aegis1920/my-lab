package application.jdkdynamicproxy;

public class SomeClazzForJdkDynamicProxy implements JdkDynamicProxy {

    @Override
    public String someMethod() {
        return "someMethod를 실행하셨습니다.";
    }
}

interface JdkDynamicProxy {

    String someMethod();
}
