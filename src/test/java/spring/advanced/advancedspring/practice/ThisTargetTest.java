package spring.advanced.advancedspring.practice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.practice.payment.PaymentService;

@Slf4j
@SpringBootTest
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {

    @Autowired
    PaymentService paymentService;

    @Test
    void test() {
        log.info("paymentService Proxy={}", paymentService.getClass());
        paymentService.payment(100);
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        /**
         * CGLIB Proxy 객체를 보고 판단 합니다.
         * this는 부모타입을 허용하기 때문에 AOP가 적용됩니다.
         */
        @Around("this(spring.advanced.advancedspring.practice.payment.PaymentService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        /**
         * target 객체(PaymentServiceImpl)를 보고 판단 합니다.
         * target은 부모타입을 허용하기 때문에 AOP가 적용됩니다.
         */
        @Around("target(spring.advanced.advancedspring.practice.payment.PaymentService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        /**
         * CGLIB Proxy 객체를 보고 판단 합니다.
         * CGLIB Proxy 객체는 PaymentServiceImpl를 상속받아서 생성하기 때문에
         * AOP 적용 대상 입니다.
         */
        @Around("this(spring.advanced.advancedspring.practice.payment.PaymentServiceImpl)")
        public Object doThisConcrete(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-concrete] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        /**
         * target 객체(PaymentServiceImpl)를 보고 판단 합니다.
         * target은 부모타입을 허용하기 때문에 AOP가 적용됩니다.
         */
        @Around("target(spring.advanced.advancedspring.practice.payment.PaymentServiceImpl)")
        public Object doTargetConcrete(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-concrete] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
