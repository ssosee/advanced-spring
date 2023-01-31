package spring.advanced.advancedspring.payment.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public AspectPayment.LogAspect logAspect() {
        return new AspectPayment.LogAspect();
    }

    @Bean
    public AspectPayment.TimeAspect timeAspect() {
        return new AspectPayment.TimeAspect();
    }
}
