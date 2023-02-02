package spring.advanced.advancedspring.exam;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class TraceAspect {

    // @Trace가 붙은 메소드에 어드바이스를 적용
    @Before("@annotation(spring.advanced.advancedspring.exam.annotation.Trace)")
    public void doTrace(JoinPoint joinPoint) {
        log.info("[trace] {} args={}", joinPoint.getSignature(), joinPoint.getArgs());
    }
}
