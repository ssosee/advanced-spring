package spring.advanced.advancedspring.config.v6_aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.config.AppV1Config;
import spring.advanced.advancedspring.config.AppV2Config;
import spring.advanced.advancedspring.config.v6_aop.aspect.LogTraceAspect;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
