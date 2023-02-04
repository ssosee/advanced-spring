package spring.advanced.advancedspring.internalcall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.internalcall.aop.CallLogAspect;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callServiceV2;

    @Test
    void external() {
        callServiceV2.external();
    }
}