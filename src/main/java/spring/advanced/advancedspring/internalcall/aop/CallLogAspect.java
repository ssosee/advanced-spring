package spring.advanced.advancedspring.internalcall.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class CallLogAspect {

    @Before("execution(* spring.advanced.advancedspring.internalcall..*.*(..))")
    public void doLog(JoinPoint joinPoint) throws Throwable {
        log.info("aop={}", joinPoint.getSignature());
    }
}
