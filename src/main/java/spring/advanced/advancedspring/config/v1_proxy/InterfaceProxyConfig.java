package spring.advanced.advancedspring.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.advanced.advancedspring.app.v1.*;
import spring.advanced.advancedspring.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import spring.advanced.advancedspring.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import spring.advanced.advancedspring.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;
import spring.advanced.advancedspring.trace.logtrace.ThreadLocalLogTrace;

/**
 * 실제 객체는 프록시 객체를 통해서 참조됨
 */
@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl orderControllerImpl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(orderControllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl orderServiceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(orderServiceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(orderRepositoryImpl, logTrace);
    }
}
