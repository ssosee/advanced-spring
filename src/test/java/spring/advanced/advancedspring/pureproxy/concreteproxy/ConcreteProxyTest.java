package spring.advanced.advancedspring.pureproxy.concreteproxy;

import org.junit.jupiter.api.Test;
import spring.advanced.advancedspring.pureproxy.concreteproxy.code.ConcreteClient;
import spring.advanced.advancedspring.pureproxy.concreteproxy.code.ConcreteLogic;
import spring.advanced.advancedspring.pureproxy.concreteproxy.code.TimeProxy;

public class ConcreteProxyTest {
    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);

        client.execute();
    }

    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient client = new ConcreteClient(timeProxy);

        client.execute();
    }
}
