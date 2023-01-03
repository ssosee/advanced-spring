package spring.advanced.advancedspring.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {
    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 반드시 던져주어야 한다.
        }
    }
}
/**
 * 1초에 2번 GET http://localhost:8080/v3/request?itemId=hello
 * 동시성 문제 발생.... ㅠ0ㅠ
 *
 * FieldLogTrace는 싱글톤으로 등록된 스프링빈 입니다.
 * 이 객체의 인스턴스가 애플리케이션에 1개만 존재한다는 의미 입니다.
 * 1초데 2번 GET을 하게되면
 * 2개의 스레드가 생성되고
 * 이 스레드가 동시에 FieldTrace.traceIdHolder 필드를 2개의 스레드가 동시에 접근하기 때문에 발생합니다.
 *
 * 2023-01-03 00:10:56.174  INFO 3243 --- [nio-8080-exec-3] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] OrderController.request()
 * 2023-01-03 00:10:56.178  INFO 3243 --- [nio-8080-exec-3] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |--->OrderService.orderItem()
 * 2023-01-03 00:10:56.178  INFO 3243 --- [nio-8080-exec-3] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |    |--->OrderRepository.save()
 * 2023-01-03 00:10:57.183  INFO 3243 --- [nio-8080-exec-3] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |    |<---OrderRepository.save() time=1005ms
 * 2023-01-03 00:10:57.184  INFO 3243 --- [nio-8080-exec-3] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |<---OrderService.orderItem() time=1006ms
 * 2023-01-03 00:10:57.184  INFO 3243 --- [nio-8080-exec-3] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] OrderController.request() time=1010ms
 *
 * 2023-01-03 00:10:56.373  INFO 3243 --- [nio-8080-exec-4] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |    |    |--->OrderController.request()
 * 2023-01-03 00:10:56.374  INFO 3243 --- [nio-8080-exec-4] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |    |    |    |--->OrderService.orderItem()
 * 2023-01-03 00:10:56.374  INFO 3243 --- [nio-8080-exec-4] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |    |    |    |    |--->OrderRepository.save()
 * 2023-01-03 00:10:57.379  INFO 3243 --- [nio-8080-exec-4] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |    |    |    |    |<---OrderRepository.save() time=1005ms
 * 2023-01-03 00:10:57.380  INFO 3243 --- [nio-8080-exec-4] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |    |    |    |<---OrderService.orderItem() time=1006ms
 * 2023-01-03 00:10:57.382  INFO 3243 --- [nio-8080-exec-4] s.a.a.trace.logtrace.FieldLogTrace       : [4db796d2] |    |    |<---OrderController.request() time=1008ms
 */

/**
 * ThreadLocal 적용
 * 동시성 문제 해결
 *
 * 2023-01-03 09:48:33.573  INFO 13532 --- [nio-8080-exec-2] s.a.a.t.logtrace.ThreadLocalLogTrace     : [e21d7063] OrderController.request()
 * 2023-01-03 09:48:33.574  INFO 13532 --- [nio-8080-exec-2] s.a.a.t.logtrace.ThreadLocalLogTrace     : [e21d7063] |--->OrderService.orderItem()
 * 2023-01-03 09:48:33.575  INFO 13532 --- [nio-8080-exec-2] s.a.a.t.logtrace.ThreadLocalLogTrace     : [e21d7063] |    |--->OrderRepository.save()
 * 2023-01-03 09:48:34.580  INFO 13532 --- [nio-8080-exec-2] s.a.a.t.logtrace.ThreadLocalLogTrace     : [e21d7063] |    |<---OrderRepository.save() time=1005ms
 * 2023-01-03 09:48:34.580  INFO 13532 --- [nio-8080-exec-2] s.a.a.t.logtrace.ThreadLocalLogTrace     : [e21d7063] |<---OrderService.orderItem() time=1006ms
 * 2023-01-03 09:48:34.581  INFO 13532 --- [nio-8080-exec-2] s.a.a.t.logtrace.ThreadLocalLogTrace     : [e21d7063] OrderController.request() time=1008ms
 *
 * 2023-01-03 09:48:33.722  INFO 13532 --- [nio-8080-exec-4] s.a.a.t.logtrace.ThreadLocalLogTrace     : [129ba763] OrderController.request()
 * 2023-01-03 09:48:33.723  INFO 13532 --- [nio-8080-exec-4] s.a.a.t.logtrace.ThreadLocalLogTrace     : [129ba763] |--->OrderService.orderItem()
 * 2023-01-03 09:48:33.723  INFO 13532 --- [nio-8080-exec-4] s.a.a.t.logtrace.ThreadLocalLogTrace     : [129ba763] |    |--->OrderRepository.save()
 * 2023-01-03 09:48:34.728  INFO 13532 --- [nio-8080-exec-4] s.a.a.t.logtrace.ThreadLocalLogTrace     : [129ba763] |    |<---OrderRepository.save() time=1005ms
 * 2023-01-03 09:48:34.729  INFO 13532 --- [nio-8080-exec-4] s.a.a.t.logtrace.ThreadLocalLogTrace     : [129ba763] |<---OrderService.orderItem() time=1006ms
 * 2023-01-03 09:48:34.730  INFO 13532 --- [nio-8080-exec-4] s.a.a.t.logtrace.ThreadLocalLogTrace     : [129ba763] OrderController.request() time=1008ms
 */