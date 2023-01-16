package spring.advanced.advancedspring.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    /**
     * 프록시로 부가 기능 추가
     */
    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String result = component.operation();
        String decoResult = "****** " + result + " ******";
        log.info("MessageDecorator 꾸미기 적용 전={}, 적용 후={}", result, decoResult);

        return decoResult;
    }
}
