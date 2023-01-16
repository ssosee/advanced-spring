package spring.advanced.advancedspring.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import spring.advanced.advancedspring.proxy.cglib.code.TimeMethodInterceptor;
import spring.advanced.advancedspring.proxy.common.service.ConcreteService;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        // CGLIB는 Enhancer 를 사용해서 프록시를 생성한다.
        Enhancer enhancer = new Enhancer();
        // CGLIB는 구체 클래스를 상속 받아서 프록시를 생성
        enhancer.setSuperclass(ConcreteService.class);
        // 프록시에 적용할 실행 로직을 할당한다.
        enhancer.setCallback(new TimeMethodInterceptor(target));
        // 프록시를 생성
        ConcreteService proxy = (ConcreteService) enhancer.create();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

    }
}
