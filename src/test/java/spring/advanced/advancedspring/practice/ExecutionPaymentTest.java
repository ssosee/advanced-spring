package spring.advanced.advancedspring.practice;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.advanced.advancedspring.practice.payment.PaymentService;
import spring.advanced.advancedspring.practice.payment.PaymentServiceImpl;

import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
public class ExecutionPaymentTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method method;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        method = PaymentServiceImpl.class.getMethod("payment", int.class);
    }

    @Test
    void exactMath() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(public String spring.advanced.advancedspring.practice.payment.PaymentServiceImpl.payment(int))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* payment(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* *ment(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* minkai(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMath1() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* spring.advanced.advancedspring.practice.payment.PaymentServiceImpl.payment(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* spring.advanced.advancedspring.practice.payment.*.*(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactSubPackage() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* spring.advanced.advancedspring.practice..*.*(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* spring.advanced.advancedspring.practice.*.*(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isFalse();
    }

    @Test
    void typeMatchSuperType() {
        // execution(접근제어자? 반환타입 선언타입?메소드이름(파라미터) 예외?)
        pointcut.setExpression("execution(* spring.advanced.advancedspring.practice.payment.PaymentService.*(..))");
        Assertions.assertThat(pointcut.matches(method, PaymentServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchChild() throws NoSuchMethodException {
        pointcut.setExpression("execution(* spring.advanced.advancedspring.practice.payment.PaymentServiceImpl.*(..))");
        Method childMethod = PaymentServiceImpl.class.getMethod("paymentChild", int.class);
        Assertions.assertThat(pointcut.matches(childMethod, PaymentServiceImpl.class)).isTrue();
    }

    // PaymentService에는 paymentChild()라는 메소드가 존재하지 않음
    @Test
    void typeMatchChildFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* spring.advanced.advancedspring.practice.payment.PaymentService.*(..))");
        Method childMethod = PaymentServiceImpl.class.getMethod("paymentChild", int.class);
        Assertions.assertThat(pointcut.matches(childMethod, PaymentServiceImpl.class)).isFalse();
    }
}
