package spring.advanced.advancedspring.proxy.pureproxy.concreteproxy;

import org.junit.jupiter.api.Test;
import spring.advanced.advancedspring.proxy.pureproxy.concreteproxy.code.TimeProxy;
import spring.advanced.advancedspring.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import spring.advanced.advancedspring.proxy.pureproxy.concreteproxy.code.ConcreteLogic;

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
