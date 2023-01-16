package spring.advanced.advancedspring.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import spring.advanced.advancedspring.app.v1.OrderControllerV1;
import spring.advanced.advancedspring.app.v1.OrderRepositoryV1;
import spring.advanced.advancedspring.app.v1.OrderServiceV1;
import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {

    private final OrderControllerV1 target;
    private final LogTrace logTrace;

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            System.out.println(target);
            status = logTrace.begin("OrderController.request()");
            String result = target.request(itemId);// 타켓 호출
            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
