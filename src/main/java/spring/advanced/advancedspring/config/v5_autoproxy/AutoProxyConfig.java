package spring.advanced.advancedspring.config.v5_autoproxy;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.config.AppV1Config;
import spring.advanced.advancedspring.config.AppV2Config;
import spring.advanced.advancedspring.config.v3_proxyfactory.advice.LogTraceAdvice;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

// @Configuration을 사용하면 해당 자바 설정 파일에 프록시를 적용
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    /**
     * 빈 후처리기는 이제 등록하지 않아도 된다.
     * 스프링은 자동 프록시 생성기라는 ( AnnotationAwareAspectJAutoProxyCreator ) 빈 후처리기를 자동으로 등록해준다.
     */
    //@Bean
    public Advisor advisor1(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    //@Bean
    public Advisor advisor2(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // package 를 기준으로 포인트컷 매칭
        pointcut.setExpression("execution(* spring.advanced.advancedspring.app..*(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // noLog() 메서드 제외
        pointcut.setExpression("execution(* spring.advanced.advancedspring.app..*(..)) " +
                "&& !execution(* spring.advanced.advancedspring.app..noLog(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
