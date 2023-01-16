package spring.advanced.advancedspring.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    // 호출할 대상 클래스
    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    /**
     * @param o             : CGLIB가 적용된 객체
     * @param method        : 호출된 메서드
     * @param objects       : 메서드를 호출하면서 전달된 인수
     * @param methodProxy   : 메서드 호출에 사용
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        // 실제 대상을 동적으로 호출
        // 성능상 MethodProxy 사용 권장
        Object result = methodProxy.invoke(target, objects);

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);

        return result;
    }
}
