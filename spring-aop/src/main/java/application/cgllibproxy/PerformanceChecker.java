package application.cgllibproxy;

import java.lang.reflect.Method;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class PerformanceChecker implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) {
        Object result = null;

        try {
            long start = System.currentTimeMillis();
            result = methodProxy.invokeSuper(o, args);
            long end = System.currentTimeMillis();

            System.out.println("수행 시간 : " + (end - start));
        } catch (Throwable e) {
            System.out.println("예외!");
        }
        return result;
    }
}
