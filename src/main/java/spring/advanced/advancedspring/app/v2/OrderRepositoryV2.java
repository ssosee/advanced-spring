package spring.advanced.advancedspring.app.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.advanced.advancedspring.trace.TraceId;
import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.hellotrace.HelloTraceV2;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final HelloTraceV2 trace;
    public void save(TraceId traceId, String itemId) {

        TraceStatus status = null;

        try {
            status = trace.beginSync(traceId, "OrderRepository.save()");
            if(itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!V2");
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
