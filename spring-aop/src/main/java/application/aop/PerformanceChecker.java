package application.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// @Aspect 혼자로는 빈 등록이 되지 않는다.
@Aspect
@Component
public class PerformanceChecker {

    @Around("execution(* application.aop.SomeClazz.someMethod())")
    public Object calculatePerformanceTime(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        try {
            long start = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();

            System.out.println("수행 시간 : " + (end - start));
        } catch (Throwable throwable) {
            System.out.println("예외 발생!");
        }
        return result;
    }
}
