package spring.advanced.advancedspring.practice.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.advanced.advancedspring.practice.internalcall.aop.MyLogAspect;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Import(MyLogAspect.class)
class MyCallServiceV0Test {

    @Autowired
    MyCallServiceV0 myCallServiceV0;

    @Test
    void external() {
        myCallServiceV0.external();
    }

    @Test
    void internal() {
        myCallServiceV0.internal();
    }
}