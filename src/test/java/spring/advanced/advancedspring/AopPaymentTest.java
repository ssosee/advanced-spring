package spring.advanced.advancedspring;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.advanced.advancedspring.payment.PaymentRepository;
import spring.advanced.advancedspring.payment.PaymentService;

@Slf4j
@SpringBootTest
public class AopPaymentTest {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentService paymentService;

    @Test
    void appInfo() {
        log.info("isAopProxy, paymentService={}", AopUtils.isAopProxy(paymentService));
        log.info("isAopProxy, paymentRepository={}", AopUtils.isAopProxy(paymentRepository));
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> paymentService.payment(-100))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void success() {
        paymentService.payment(100);
    }
}
