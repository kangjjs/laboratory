package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Around("execution(* com.example.demo.service.*.*(..))") // 서비스 계층의 모든 메서드 대상
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();

		Object result = joinPoint.proceed(); // 실제 메서드 실행

		long end = System.currentTimeMillis();
		log.info("[AOP] {} 실행 시간: {}ms", joinPoint.getSignature(), (end - start));

		return result;
	}
}

