package spring.advanced.advancedspring.practice.proxyvs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import spring.advanced.advancedspring.member.MemberServiceImpl;
import spring.advanced.advancedspring.practice.payment.PaymentService;
import spring.advanced.advancedspring.practice.payment.PaymentServiceImpl;

@Slf4j
public class MyProxyCastingTest {
    @Test
    void jdkProxy() {
        PaymentServiceImpl target = new PaymentServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        PaymentService paymentServiceProxy = (PaymentService) proxyFactory.getProxy();
        log.info("proxy class={}", paymentServiceProxy.getClass());

        // 프록시를 구체클래스로 캐스팅 실패
        Assertions.assertThrows(ClassCastException.class, () -> {
            PaymentServiceImpl paymentServiceImplProxy = (PaymentServiceImpl) proxyFactory.getProxy();
        });
    }

    @Test
    void cglibProxy() {
        PaymentServiceImpl target = new PaymentServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        PaymentService paymentServiceProxy = (PaymentService) proxyFactory.getProxy();
        log.info("casting1 proxy class={}", paymentServiceProxy.getClass());

        // CGLIB 프록시를 구현 클래스로 캐스팅 시도
        PaymentServiceImpl paymentServiceImplProxy = (PaymentServiceImpl) proxyFactory.getProxy();
        log.info("casting2 proxy class={}", paymentServiceImplProxy.getClass());
    }
}
