package spring.advanced.advancedspring.practice.payment;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.advanced.advancedspring.member.annotation.MethodAop;
import spring.advanced.advancedspring.practice.payment.annotation.MyMethodAop;

@Component
public class PaymentServiceImpl implements PaymentService {

    @MyMethodAop("테스트 입니다.")
    @Override
    public String payment(int price) {
        return "가격은 "+price+" 입니다.";
    }

    public String paymentChild(int price) {
        return price+" 자식입니다.";
    }
}
