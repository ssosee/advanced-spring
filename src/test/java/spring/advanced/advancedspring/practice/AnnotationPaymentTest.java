package spring.advanced.advancedspring.practice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.assertj.core.error.AssertJMultipleFailuresError;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.member.annotation.MethodAop;
import spring.advanced.advancedspring.practice.payment.PaymentService;
import spring.advanced.advancedspring.practice.payment.annotation.MyMethodAop;

import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
@Import(AnnotationPaymentTest.AtAnnotationAspect.class)
public class AnnotationPaymentTest {
    @Autowired
    PaymentService paymentService;

    @Test
    void success() {
        log.info("memberService={}", paymentService.getClass());
        paymentService.payment(1000);
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect {
        @Around(value = "@annotation(spring.advanced.advancedspring.practice.payment.annotation.MyMethodAop)")
        public Object doAtAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
