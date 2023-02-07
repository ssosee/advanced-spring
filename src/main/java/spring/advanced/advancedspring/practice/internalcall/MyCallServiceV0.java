package spring.advanced.advancedspring.practice.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyCallServiceV0 {

    public void external() {
        log.info("external 호출");
        internal(); // 외부 메소드 호출
    }

    public void internal() {
        log.info("internal 호출");
    }
}
