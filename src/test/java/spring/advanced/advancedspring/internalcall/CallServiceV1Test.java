package spring.advanced.advancedspring.internalcall;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.internalcall.aop.CallLogAspect;

@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV1Test {

    @Autowired
    CallServiceV1 callServiceV1;

    @Test
    void external() {
        callServiceV1.external();
    }

    @Test
    void internal() {
        callServiceV1.internal();
    }
}