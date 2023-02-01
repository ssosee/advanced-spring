package spring.advanced.advancedspring.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.member.MemberService;

@Slf4j
@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK 동적 프록시
//@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // CGLIB 프록시
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }


    /**
     * JDK 동적 프록시 생성   : 인터페이스가 필수이고, 인터페이스를 구현한 프록시 객체를 생성
     * CGLIB 프록시 생성     : 인터페이스가 있어도 구체 클래스를 상속 받아서 프록시 객체를 생성
     */
    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        // 부모 타입 허용
        // this는 프록시 객체(MemberService를 상속 받은 프록시 객체)를 대상으로 포인트컷 매칭
        @Around("this(spring.advanced.advancedspring.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모 타입 허용
        // target은 실제 target 객체(MemberServiceImpl 객체)를 대상으로 포인트컷 매칭
        @Around("target(spring.advanced.advancedspring.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // JDK 동적 프록시 : this는 프록시 객체(MemberService를 상속 받은 프록시 객체)를 대상으로 포인트컷 매칭
        // CGLIB        : this는 프록시 객체(MemberServiceImpl을 상속 받은 프록시 객체)를 대상으로 포인트컷 매칭
        @Around("this(spring.advanced.advancedspring.member.MemberServiceImpl)")
        public Object doThisConcrete(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-concrete] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // target은 실제 target 객체(MemberServiceImpl 객체)를 대상으로 포인트컷 매칭
        @Around("target(spring.advanced.advancedspring.member.MemberServiceImpl)")
        public Object doTargetConcrete(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-concrete] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
