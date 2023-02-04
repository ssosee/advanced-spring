package spring.advanced.advancedspring.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * internal()에 AOP 적용 안됨
 */
@Slf4j
@Component
public class CallServiceV0 {

    public void external() {
        log.info("call external");
        internal(); // 내부 메소드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
