package spring.advanced.advancedspring.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    // @Around는 포인트 컷
    @Around("execution(* spring.advanced.advancedspring.order..*(..))")
    // doLog는 어드바이스
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    public Object doLog2(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("");
        return joinPoint.proceed();
    }
}
