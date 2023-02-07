package spring.advanced.advancedspring.practice.proxyvs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.practice.internalcall.aop.MyLogAspect;
import spring.advanced.advancedspring.practice.payment.PaymentService;
import spring.advanced.advancedspring.practice.payment.PaymentServiceImpl;
import spring.advanced.advancedspring.practice.proxyvs.code.MyProxyDIAspect;

@Slf4j
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // CGLIB
@Import(MyProxyDIAspect.class)
public class MyProxyDITest {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentServiceImpl paymentServiceImpl;

    @Test
    void test() {
        log.info("paymentService class={}", paymentService.getClass());
        log.info("paymentServiceImpl class={}", paymentServiceImpl.getClass());
        paymentServiceImpl.payment(100);
    }
}
