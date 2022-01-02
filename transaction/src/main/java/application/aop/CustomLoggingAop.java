package application.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Slf4j
@Component
public class CustomLoggingAop {
	@Around("@annotation(CustomLogging)")
	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
		Object target = joinPoint.getTarget();
		
		log.info("-------------------------{} start----------------------------", target.getClass().getSimpleName());
		log.info("{} Transaction Name : {}", target.getClass().getSimpleName(), TransactionSynchronizationManager.getCurrentTransactionName());
		
		Object proceed = joinPoint.proceed();
		
		log.info("-------------------------{} end----------------------------", target.getClass().getSimpleName());
		return proceed;
	}
	
}
