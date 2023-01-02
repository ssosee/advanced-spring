package spring.advanced.advancedspring.app.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.hellotrace.HelloTraceV1;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final HelloTraceV1 trace;
    public void save(String itemId) {

        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepository.save()");
            if(itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!V1");
            }
            sleep(1000); // 상품을 저장하는데 1초정도 걸리는 것으로 가정
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
