package spring.advanced.advancedspring.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import spring.advanced.advancedspring.app.v1.OrderRepositoryV1;
import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.logtrace.FieldLogTrace;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {

    private final OrderRepositoryV1 target;
    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderRepository.save()");
            target.save(itemId); // 타켓 호출
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
