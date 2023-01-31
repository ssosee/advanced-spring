package spring.advanced.advancedspring.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PaymentRepository {

    public void save(int price) {
        log.info("[PaymentRepository 실행]={}", price);
        if(price < 0) {
            throw new IllegalStateException("[PaymentRepository 예외발생]");
        }
    }
}
