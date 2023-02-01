package spring.advanced.advancedspring.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.member.MemberService;
import spring.advanced.advancedspring.member.annotation.ClassAop;

@Slf4j
@Import({AtTargetAtWithinTest.Config.class})
@SpringBootTest
public class AtTargetAtWithinTest {

    @Autowired
    Child child;
    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("child Proxy={}", child.getClass());
        child.childMethod(); // 부모, 자식 모두 있는 메소드
        child.parentMethod(); // 부모 클래스만 있는 메소드
    }

    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }

        @Bean
        public Child child() {
            return new Child();
        }

        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new AtTargetAtWithinAspect();
        }
    }

    static class Parent {
        public void parentMethod() {}
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod() {}
    }

    @Slf4j
    @Aspect
    static class AtTargetAtWithinAspect {

        // @target: 인스턴스 기준으로 모든 메소드의 조인 포인트를 선정, 부모 타입의 메소드도 적용
        @Around("execution(* spring.advanced.advancedspring.pointcut..*(..)) " +
                "&& @target(spring.advanced.advancedspring.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // @within: 선택된 클래스 내부에 있는 메소드만 조인 포인트로 선정, 부모 타입의 메소드는 적용되지 않음
        @Around("execution(* spring.advanced.advancedspring.pointcut..*(..)) " +
                "&& @within(spring.advanced.advancedspring.member.annotation.ClassAop)")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
