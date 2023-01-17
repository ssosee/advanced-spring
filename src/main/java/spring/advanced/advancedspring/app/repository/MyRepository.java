package spring.advanced.advancedspring.app.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class MyRepository {

    private String name;

    public void save(String name) {
        sleep(1000);
        this.name = name;
        log.info("저장 name={}", name);
    }

    public String find() {
        log.info("조회 name={}", name);
        return name;
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
