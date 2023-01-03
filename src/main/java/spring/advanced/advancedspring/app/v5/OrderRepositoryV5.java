package spring.advanced.advancedspring.app.v5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.advanced.advancedspring.trace.callback.TraceTemplate;
import spring.advanced.advancedspring.trace.logtrace.LogTrace;
import spring.advanced.advancedspring.trace.template.AbstractTemplate;

@Repository
public class OrderRepositoryV5 {

    private final LogTrace trace;
    private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace trace) {
        this.trace = trace;
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId) {
        template.execute("OrderRepository.save()", () -> {
            if(itemId.equals("ex")) {
                throw new IllegalStateException("예외발생V5");
            }
            sleep(1000);
            return null;
        });
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
