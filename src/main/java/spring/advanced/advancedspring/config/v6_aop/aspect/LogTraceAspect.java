package spring.advanced.advancedspring.config.v6_aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

/**
 * AnnotationAwareAspectJAutoProxyCreator는 Advisor 를 자동으로 찾아와서
 * 필요한 곳에 프록시를 생성하고 적용해준다고 했다.
 * 자동 프록시 생성기는 여기에 추가로 하나의 역할을 더 하는데,
 * 바로 @Aspect 를 찾아서 이것을 Advisor 로 만들어준다.
 */
@Slf4j
@Aspect //애노테이션 기반 프록시를 적용
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // @Around 의 메서드는 어드바이스
    @Around("execution(* spring.advanced.advancedspring.app..*(..))") //포인트컷 표현식
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;

//        log.info("target={}", joinPoint.getTarget()); //실제 호출 대상
//        log.info("getArgs={}", joinPoint.getArgs()); //전달인자
//        log.info("getSignature={}", joinPoint.getSignature()); //join point 시그니처

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // 로직 호출(target 호출)
            Object result = joinPoint.proceed();

            logTrace.end(status);

            return result;

        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
