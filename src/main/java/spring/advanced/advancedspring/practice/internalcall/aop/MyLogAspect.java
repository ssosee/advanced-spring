package spring.advanced.advancedspring.practice.internalcall.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class MyLogAspect {

    @Before("execution(* spring.advanced.advancedspring.practice.internalcall..*.*(..))")
    public void doLog(JoinPoint joinPoint) throws Throwable {
        log.info("[AOP 적용] {}", joinPoint.getSignature());
    }
}
