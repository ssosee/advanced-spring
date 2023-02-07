package spring.advanced.advancedspring.practice.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyCallServiceV1 {

    private final ApplicationContext applicationContext;
    private final ObjectProvider<MyCallServiceV1> myCallServiceV1Provider;

    public void external() {
        log.info("external 호출");
        // 지연 조회
        MyCallServiceV1 myCallServiceV1 = applicationContext.getBean(MyCallServiceV1.class);
        myCallServiceV1.internal(); // 외부 메소드 호출
    }

    public void internal() {
        log.info("internal 호출");
    }
}
