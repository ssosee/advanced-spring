package spring.advanced.advancedspring.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void payment(int price) {
        log.info("[PaymentService 실행]");
        paymentRepository.save(price);
    }
}
