package spring.advanced.advancedspring;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.order.OrderRepository;
import spring.advanced.advancedspring.order.OrderService;
import spring.advanced.advancedspring.order.aop.AspectV4Pointcut;
import spring.advanced.advancedspring.order.aop.AspectV5Order;
import spring.advanced.advancedspring.order.aop.AspectV6Advice;

@Slf4j
//@Import(AspectV1.class)
//@Import(AspectV2.class)
//@Import(AspectV3.class)
//@Import(AspectV4Pointcut.class)
//@Import({AspectV5Order.LogAspect.class, AspectV5Order.TransactionAspect.class})
@Import(AspectV6Advice.class)
@SpringBootTest
public class AopTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @Test
    void appInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }
}
