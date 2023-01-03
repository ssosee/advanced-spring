package spring.advanced.advancedspring.trace.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import spring.advanced.advancedspring.trace.template.code.AbstractTemplate;
import spring.advanced.advancedspring.trace.template.code.SubClassLogic1;
import spring.advanced.advancedspring.trace.template.code.SubClassLogic2;

/**
 * 템플릿 메소드 패턴을 사용하면,
 * 변하는 부분과 변하지 않는 부분을 분리하여 모듈화 할 수 있다.
 */
@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        // 비지니스 로직 실행
        log.info("비지니스 로직1 실행");
        // 비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}ms", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        // 비지니스 로직 실행
        log.info("비지니스 로직2 실행");
        // 비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}ms", resultTime);
    }

    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        AbstractTemplate template2 = new SubClassLogic2();

        template1.execute();
        template2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비지니스 로직1 실행");
            }
        };

        AbstractTemplate template2 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비지니스 로직2 실행");
            }
        };

        log.info("클래스 이름1={}",template1.getClass());
        log.info("클래스 이름2={}",template2.getClass());
        template1.execute();
        template2.execute();
    }
}
