package spring.advanced.advancedspring.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import spring.advanced.advancedspring.trace.strategy.code.template.Callback;
import spring.advanced.advancedspring.trace.strategy.code.template.TimeLogTemplate;

/**
 * 템플릿 콜백 패턴
 */
@Slf4j
public class TemplateCallbackTest {
    @Test
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(new Callback() {
            @Override
            public void call() {
                log.info("비지니스 로직1 실행");
            }
        });
    }

    @Test
    void callbackV2() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비지니스 로직1 실행"));
    }
}
