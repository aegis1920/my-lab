package application.jdkdynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// JDK Dynamic Proxy를 이용한 방법
public class PerformanceChecker implements InvocationHandler {

    private final Object target;

    public PerformanceChecker(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        long end = System.currentTimeMillis();

        System.out.println("수행 시간 : " + (end - start));
        return result;
    }
}
