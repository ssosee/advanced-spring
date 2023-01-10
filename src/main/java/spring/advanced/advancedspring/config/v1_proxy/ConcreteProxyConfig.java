package spring.advanced.advancedspring.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.advanced.advancedspring.app.v1.OrderServiceV1Impl;
import spring.advanced.advancedspring.app.v2.OrderControllerV2;
import spring.advanced.advancedspring.app.v2.OrderRepositoryV2;
import spring.advanced.advancedspring.app.v2.OrderServiceV2;
import spring.advanced.advancedspring.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import spring.advanced.advancedspring.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import spring.advanced.advancedspring.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

/**
 * 클래스를 상속받아서 프록시 구현
 */
@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderRepositoryV2 orderRepository(LogTrace logTrace) {
        OrderRepositoryV2 orderRepository = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(orderRepository, logTrace);
    }

    @Bean
    public OrderServiceV2 orderService(LogTrace logTrace) {
        OrderServiceV2 orderService = new OrderServiceV2(orderRepository(logTrace));
        return new OrderServiceConcreteProxy(orderService, logTrace);
    }

    @Bean
    public OrderControllerV2 orderController(LogTrace logTrace) {
        OrderControllerV2 orderController = new OrderControllerV2(orderService(logTrace));
        return new OrderControllerConcreteProxy(orderController, logTrace);
    }
}
