package spring.advanced.advancedspring.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRepository {
    private String name;

    public void logic(String name) {
        save(name);
        sleep(1000);
        find();
    }

    public void save(String name) {
        this.name = name;
        log.info("저장 name={}", name);
    }

    public String find() {
        log.info("조회 name={}", this.name);
        return this.name;
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
