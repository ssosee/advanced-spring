package spring.advanced.advancedspring.practice.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyCallExternalService {

    private final MyCallInternalService myCallInternalService;

    public void external() {
        log.info("external 호출");
        myCallInternalService.internal();
    }
}
