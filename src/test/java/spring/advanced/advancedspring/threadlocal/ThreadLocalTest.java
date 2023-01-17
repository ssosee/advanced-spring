package spring.advanced.advancedspring.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ThreadLocalTest {

    TestRepository testRepository = new TestRepository();
    TestRepositoryApplyThreadLocal testRepositoryApplyThreadLocal = new TestRepositoryApplyThreadLocal();

    // CountDownLatch는 어떤 쓰레드가 다른 쓰레드에서 작업이 완료될 때 까지 기다릴 수 있도록 해주는 클래스
    private CountDownLatch countDownLatch = new CountDownLatch(3);

    @Test
    void 동시성문제() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testRepository.logic("쿼카1");
                countDownLatch.countDown();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                testRepository.logic("쿼카2");
                countDownLatch.countDown();
            }
        });
        thread1.setName("쿼카1 스레드");
        thread1.start();

        testRepository.sleep(100);

        thread2.setName("쿼카2 스레드");
        thread2.start();

        countDownLatch.countDown(); // 메인 스레드 카운트
        countDownLatch.await(); // Latch의 숫자가 0이 될 때까지 기다림

        log.info("테스트 끝!");
    }

    @Test
    void 동시성문제해결() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testRepositoryApplyThreadLocal.logic("쿼카1");
                countDownLatch.countDown();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                testRepositoryApplyThreadLocal.logic("쿼카2");
                countDownLatch.countDown();
            }
        });
        thread1.setName("쿼카1 스레드");
        thread1.start();

        testRepositoryApplyThreadLocal.sleep(10);

        thread2.setName("쿼카2 스레드");
        thread2.start();

        countDownLatch.countDown(); // 메인 스레드 카운트
        countDownLatch.await(); // Latch의 숫자가 0이 될 때까지 기다림

        log.info("테스트 끝!");
    }
}
