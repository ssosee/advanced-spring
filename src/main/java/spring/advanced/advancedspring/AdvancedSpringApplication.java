package spring.advanced.advancedspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.config.AppV1Config;
import spring.advanced.advancedspring.config.AppV2Config;
import spring.advanced.advancedspring.config.v1_proxy.ConcreteProxyConfig;
import spring.advanced.advancedspring.config.v1_proxy.InterfaceProxyConfig;
import spring.advanced.advancedspring.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import spring.advanced.advancedspring.config.v2_dynamicproxy.DynamicProxyFilterConfig;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;
import spring.advanced.advancedspring.trace.logtrace.ThreadLocalLogTrace;

//@Import(AppV1Config.class) // 강제로 빈 등록(컴포트넌 스캔 대상과 무관)
//@Import({AppV1Config.class, AppV2Config.class}) // 강제로 빈 등록(컴포트넌 스캔 대상과 무관)
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
//@Import(DynamicProxyBasicConfig.class)
@Import(DynamicProxyFilterConfig.class)
@SpringBootApplication(scanBasePackages = "spring.advanced.advancedspring.app") // 컴포넌트 스캔 대상 정의
public class AdvancedSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedSpringApplication.class, args);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
