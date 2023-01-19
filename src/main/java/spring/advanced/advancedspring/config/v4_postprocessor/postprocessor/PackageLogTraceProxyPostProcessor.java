package spring.advanced.advancedspring.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 원본 객체를 프록시 객체로 변환
 */
@Slf4j
public class PackageLogTraceProxyPostProcessor implements BeanPostProcessor {

    // 특정 패키지를 기준으로 해당 패키지와 그 하위 패키지의 빈들을 프록시로 만든다.
    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTraceProxyPostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    /**
     * 객체 생성 이후에 @PostConstruct 같은
     * 초기화가 발생하기 전에 호출되는 포스트 프로세서이다.
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /**
     * 객체 생성 이후에 @PostConstruct 같은
     * 초기화가 발생한 다음에 호출되는 포스트 프로세서이다.
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("param beanName={} bean={}", beanName, bean.getClass());

        // 프록시 대상 여부 체크
        // 프록시 적용 대상이 아니면 원본을 그대로 반환
        String packageName = bean.getClass().getPackageName();
        if(!packageName.startsWith(basePackage)) {
            return bean;
        }

        // 프록시 대상이면 프록시를 만들어서 반환
        ProxyFactory factory = new ProxyFactory(bean);
        factory.addAdvisor(advisor);

        Object proxy = factory.getProxy();
        log.info("create proxy: target={} proxy={}", bean.getClass(), proxy.getClass());

        return proxy;
    }
}
