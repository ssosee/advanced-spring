package spring.advanced.advancedspring.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.member.MemberService;
import spring.advanced.advancedspring.member.annotation.ClassAop;
import spring.advanced.advancedspring.member.annotation.MethodAop;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService = Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    /**
     * 어드바이스에게 매개변수 전달
     */
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* spring.advanced.advancedspring.member..*.*(..))")
        private void allMember() {}

        // jointPoint.getArgs()[0]와 같이 매개변수를 전달 받는다.
        @Around("allMember()")
        public Object logArgs(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] {}, arg={}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        // args(arg, ..)와 같이 매개변수를 전달 받는다.
        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {}, arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        // @Before를 사용한 축약 버전, 타입을 String으로 제한
        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        // 프록시 객체를 전달 받는다.
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this] {}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        // 실제 대상 객체를 전달 받는다.
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target] {}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        // @target 타입의 애노테이션을 전달 받는다.
        @Before("allMember() && @target(annotation)")
        public void atArgs(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target] {}, obj={}", joinPoint.getSignature(), annotation);
        }

        // @within 타입의 애노테이션을 전달 받는다.
        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within] {}, obj={}", joinPoint.getSignature(), annotation);
        }

        // 메소드의 애노테이션을 전달 받는다.
        // annotation.value() 애노테이션 값 출력
        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation] {}, annotationValue={}", joinPoint.getSignature(), annotation.value());
        }
    }
}
