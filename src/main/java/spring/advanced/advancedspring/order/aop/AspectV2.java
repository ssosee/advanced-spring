package spring.advanced.advancedspring.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // 포인트컷 분리
    @Pointcut("execution(* spring.advanced.advancedspring.order..*(..))")
    private void allOrder(){} // pointcut signature(메소드와 파라미터를 합쳐서 포인트컷 시그니처라고 함)

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
