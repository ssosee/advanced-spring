package spring.advanced.advancedspring.config.v3_proxyfactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.advanced.advancedspring.app.v2.OrderControllerV2;
import spring.advanced.advancedspring.app.v2.OrderRepositoryV2;
import spring.advanced.advancedspring.app.v2.OrderServiceV2;
import spring.advanced.advancedspring.config.v3_proxyfactory.advice.LogTraceAdvice;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

/**
 * 포인트컷     : 어디에 부가 기능을 부여할 것인지 아닌지 판단(필터링 로직)
 * 어드바이스    : 프록시가 호출하는 부가 기능(프록시 로직)
 * 어드바이저    : 하나의 포인트 컷과 하나의 어드바이스를 가지고 있는 것(포인트컷1 + 어드바이스1)
 */
@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 orderControllerV2 = new OrderControllerV2(orderServiceV2(logTrace));

        ProxyFactory factory = new ProxyFactory(orderControllerV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderControllerV2 proxy = (OrderControllerV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderControllerV2.getClass());
        return proxy;
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 orderServiceV2 = new OrderServiceV2(orderRepositoryV2(logTrace));

        ProxyFactory factory = new ProxyFactory(orderServiceV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderServiceV2 proxy = (OrderServiceV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderServiceV2.getClass());
        return proxy;
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();

        ProxyFactory factory = new ProxyFactory(orderRepositoryV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderRepositoryV2 proxy = (OrderRepositoryV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderRepositoryV2.getClass());
        return proxy;
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
