package spring.advanced.advancedspring.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 지연 조회를 사용해서 의존성 주입
 * ObjectProvider, ApplicationContext 사용
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

    //private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceV2Provider;

    public void external() {
        log.info("call external");
        //CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);


        /**
         * callServiceV2Provider.getObject()를 호출하는 시점에 스프링 컨테이너에서 빈을 조회함
         * 이때 자기 자신을 주입받는 것이 아니기 때문에 순환 사이클이 발생하지 않습니다.
         */
        CallServiceV2 callServiceV2 = callServiceV2Provider.getObject();
        callServiceV2.internal(); // 외부 메소드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
