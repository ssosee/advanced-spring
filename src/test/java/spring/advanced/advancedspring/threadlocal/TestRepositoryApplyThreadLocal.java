package spring.advanced.advancedspring.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRepositoryApplyThreadLocal {
    private ThreadLocal<String> nameLocal = new ThreadLocal<>();
    public void logic(String name) {
        save(name);
        sleep(1000);
        find();
        finish(); // 스레드 로컬 제거
    }

    public void save(String name) {
        nameLocal.set(name);
        log.info("저장 name={}", name);
    }

    public String find() {
        log.info("조회 name={}", nameLocal.get());
        return nameLocal.get();
    }

    public void finish() {
        // 스레드 로컬 제거
        nameLocal.remove();
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
