package spring.advanced.advancedspring.config.v1_proxy.concrete_proxy;

import lombok.RequiredArgsConstructor;
import spring.advanced.advancedspring.app.v2.OrderRepositoryV2;
import spring.advanced.advancedspring.app.v2.OrderServiceV2;
import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;


public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        /**
         * 자바 문법에 의해
         * 자식클래스를 생성할 때는
         * 항상 부모 클래스의 생성자를 호출해야함
         */
        super(null); //부모의 기능을 안쓰고 프록시 역할만 할것이기 때문에 null로 설정
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {

        TraceStatus status = null;

        try {
            status = logTrace.begin("OrderService.orderItem()");
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
