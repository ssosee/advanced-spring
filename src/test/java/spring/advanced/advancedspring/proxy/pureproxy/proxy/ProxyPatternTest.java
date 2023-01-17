package spring.advanced.advancedspring.proxy.pureproxy.proxy;

import org.junit.jupiter.api.Test;
import spring.advanced.advancedspring.proxy.pureproxy.proxy.code.CacheProxy;
import spring.advanced.advancedspring.proxy.pureproxy.proxy.code.ProxyPatternClient;
import spring.advanced.advancedspring.proxy.pureproxy.proxy.code.Subject;
import spring.advanced.advancedspring.proxy.pureproxy.proxy.code.RealSubject;


public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        for(int i = 0; i < 3; i++) {
            client.execute();
        }
    }

    @Test
    void cacheProxyTest() {
        Subject realSubject = new RealSubject();
        Subject cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        for(int i = 0; i < 3; i++) {
            client.execute();
        }
    }
}