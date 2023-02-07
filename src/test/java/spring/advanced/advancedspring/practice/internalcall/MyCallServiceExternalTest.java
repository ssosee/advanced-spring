package spring.advanced.advancedspring.practice.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.practice.internalcall.aop.MyLogAspect;

@Slf4j
@SpringBootTest
@Import(MyLogAspect.class)
class MyCallServiceExternalTest {
    @Autowired
    MyCallExternalService myCallExternalService;

    @Test
    void external() {
        myCallExternalService.external();
    }
}