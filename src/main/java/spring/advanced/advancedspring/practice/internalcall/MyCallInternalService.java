package spring.advanced.advancedspring.practice.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyCallInternalService {

    public void internal() {
        log.info("internal 호출");
    }
}
