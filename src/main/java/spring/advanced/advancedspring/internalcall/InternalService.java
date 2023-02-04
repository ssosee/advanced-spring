package spring.advanced.advancedspring.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 별도의 클래스로 분리
 */
@Slf4j
@Component
public class InternalService {
    public void internal() {
        log.info("call internal");
    }
}
