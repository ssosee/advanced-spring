package spring.advanced.advancedspring.config.v2_dynamicproxy.handler;

import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * LogTrace를 적용할 수 있는 InvocationHandler
 */
public class LogTraceBasicHandler implements InvocationHandler {

    private final Object target; // 프록시가 호출할 대상
    private final LogTrace logTrace;

    public LogTraceBasicHandler(Object target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TraceStatus status = null;
        try {
            // logTrace에 사용할 메시지: 클래스명 + "." + 메서드명 +"()"
            // method를 통해서 호출되는 메서드 정보와 클래스 정보를 동적으로 확인 가능
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(message);
            // 로직 호출
            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
