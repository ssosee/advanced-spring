package spring.advanced.advancedspring.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static java.lang.Thread.sleep;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {
    public void sava(String itemId) {
        if(itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000); // 상품을 저장하는데 1초정도 걸리는 것으로 가정
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
