package spring.advanced.advancedspring.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 내부 호출을 해결하는 가장 간단한 방법은 자기 자신을 의존관계 주입 받는 것
 */
@Slf4j
@Component
public class CallServiceV1 {
    private CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callService={}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        // 프록시 인스턴스를 통해서 호출
        callServiceV1.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
