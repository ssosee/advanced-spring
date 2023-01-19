package spring.advanced.advancedspring.config.v4_postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.config.AppV1Config;
import spring.advanced.advancedspring.config.AppV2Config;
import spring.advanced.advancedspring.config.v3_proxyfactory.advice.LogTraceAdvice;
import spring.advanced.advancedspring.config.v4_postprocessor.postprocessor.PackageLogTraceProxyPostProcessor;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTraceProxyPostProcessor logTracePostProcessor(LogTrace logTrace) {
        return new PackageLogTraceProxyPostProcessor("spring.advanced.advancedspring.app", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
