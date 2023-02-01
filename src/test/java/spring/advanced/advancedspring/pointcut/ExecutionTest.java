package spring.advanced.advancedspring.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import spring.advanced.advancedspring.member.MemberServiceImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {
    // 포인트컷 표현식을 처리해주는 클래스
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        // execution(* ..package..Class.)
        // public java.lang.String spring.advanced.advancedspring.member.MemberServiceImpl.hello(java.lang.String)
        // execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
        log.info("helloMethod={}", helloMethod);
    }

    // 가장 정확한 포인트컷
    @Test
    void exactMatch() {
        /**
         * 접근제어자?   : public
         * 반환타입     : String
         * 선언타입?    : spring.advanced.advancedspring.member.MemberServiceImpl
         * 메소드 이름   : hello
         * 파라미터     : (String)
         * 예외?       : 생략
         */
        pointcut.setExpression("execution(public String spring.advanced.advancedspring.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 가장 많이 생략한 포인트컷
    @Test
    void allMatch() {
        /**
         * 접근제어자?   : 생략
         * 반환타입     : *
         * 선언타입?    : 생략
         * 메소드 이름   : *
         * 파라미터     : (..)
         * 예외?       : 없음
         */
        // *은 아무 값이 들어와도 된다는 의미
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 메소드 이름 매칭 관련 포인트컷
    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 패키지 매칭 관련 포인트컷
    // spring.advanced.advancedspring.member.*(1).*(2)
    // (1): 타입, (2): 메소드명
    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* spring.advanced.advancedspring.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        // execution(* spring.advanced.advancedspring.member.MemberServiceImpl.hello(..))
        pointcut.setExpression("execution(* spring.advanced.advancedspring.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse() {
        /**
         * .  : 정확하게 해당 위치의 패키지
         * .. : 해당 위치기의 패키지와 그 하위 패키지도 포함
         */
        // execution(* spring.advanced.advancedspring.member.MemberServiceImpl.hello(..))
        pointcut.setExpression("execution(* spring.advanced.advancedspring.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        /**
         * .  : 정확하게 해당 위치의 패키지
         * .. : 해당 위치기의 패키지와 그 하위 패키지도 포함
         */
        // execution(* spring.advanced.advancedspring.member.MemberServiceImpl.hello(..))
        pointcut.setExpression("execution(* spring.advanced.advancedspring.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2() {
        // execution(* spring.advanced.advancedspring.member.MemberServiceImpl.hello(..))
        pointcut.setExpression("execution(* spring.advanced.advancedspring..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 타입 매칭 -부모 타입 허용
    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* spring.advanced.advancedspring.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType() {
        // 부모타입 허용
        pointcut.setExpression("execution(* spring.advanced.advancedspring.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 타입 매칭 - 부모 타입에 있는 메소드만 허용
    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* spring.advanced.advancedspring.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    // 포인트컷으로 지정한 MemberService는 internal 이라는 이름의 메소드가 없습니다.
    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* spring.advanced.advancedspring.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    // 파라미터 매칭

    // String 타입의 파라미터 허용
    // (String)
    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터가 없어야 함
    // ()
    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 정확히 하나의 파라미터 허용, 모든 타입 허용
    // (Xxx)
    @Test
    void argsMatchStar() {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    // 파라미터가 없어도 됨 0..*
    // (), (Xxx), (Xxx, Xxx)
    @Test
    void argsMatchAll() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    // (String), (String, Xxx), (String, Xxx, Xxx) 허용
    @Test
    void argsMatchComplex() {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
