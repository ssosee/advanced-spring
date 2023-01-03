package spring.advanced.advancedspring.trace.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import spring.advanced.advancedspring.trace.threadlocal.code.FieldService;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();
    private CountDownLatch countDownLatch = new CountDownLatch(3);

    @Test
    void field() throws InterruptedException {
        log.info("main start");

        Thread threadA = new Thread(() -> {
            fieldService.logic("userA");
            countDownLatch.countDown();
        });

        Thread threadB = new Thread(() -> {
            fieldService.logic("userB");
            countDownLatch.countDown();
        });

        threadA.setName("thread-A");
        threadB.setName("thread-B");

        threadA.start();
//        fieldService.sleep(2000); // 동시성 문제 발생 X
        fieldService.sleep(100); // 동시성 문제 발생 O
        threadB.start();
        countDownLatch.countDown();
        countDownLatch.await();
        log.info("main exit");
    }
}
