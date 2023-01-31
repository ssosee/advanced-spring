package spring.advanced.advancedspring.payment.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectPayment {

    @Aspect
    @Order(1)
    public static class TimeAspect {
        @Around("spring.advanced.advancedspring.payment.aop.PointCutsPayment.paymentAndService()")
        public Object doTime(ProceedingJoinPoint joinPoint) throws Throwable {
            try {
                // @Before
                long startTime = System.currentTimeMillis();
                log.info("{} [시간측정 시작]", joinPoint.getSignature());

                Object result = joinPoint.proceed(); // 조인 포인트

                // @AfterReturning
                long endTime = System.currentTimeMillis();
                log.info("{} [걸린 시간] {}ms", joinPoint.getSignature(), endTime - startTime);

                return result;
            } catch (Exception e) {
                // @AfterThrowing
                log.info("{} [시간측정 불가] 예외 발생 {}", joinPoint.getSignature(), e.getMessage());
                throw e;
            } finally {
                // @After
                log.info("{} [시간측정 종료]", joinPoint.getSignature());
            }
        }
    }

    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("spring.advanced.advancedspring.payment.aop.PointCutsPayment.allPayment()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[LOG] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
