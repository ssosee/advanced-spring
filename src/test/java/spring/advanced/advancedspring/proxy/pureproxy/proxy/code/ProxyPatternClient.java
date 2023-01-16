package spring.advanced.advancedspring.proxy.pureproxy.proxy.code;

public class ProxyPatternClient {
    private Subject subject; // 구성 이용(composition)

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        subject.operation();
    }
}
