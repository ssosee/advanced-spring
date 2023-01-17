package spring.advanced.advancedspring.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        // target 클래스를 호출하고 결과를 받음
        // 근데 target 어디 갔니?
        Object result = invocation.proceed();
        // target 클래스의 정보는 MethodInvocation invocation에 저장되어 있다!
        // 프록시 팩토리로 프록시를 생성하는 단계에서 target정보를 파라미터로 전달함

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}ms", resultTime);

        return result;
    }
}
