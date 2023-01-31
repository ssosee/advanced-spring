package spring.advanced.advancedspring.payment.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCutsPayment {

    // spring.advanced.advancedspring.payment 하위 패키지
    @Pointcut("execution(* spring.advanced.advancedspring.payment..*(..)) " +
            "&& !target(spring.advanced.advancedspring.payment.aop.AopConfig)")
    public void allPayment(){}

    // 클래스 이름 패턴이 *Repository
    @Pointcut("execution(* *..*Repository.*(..)) ")
    public void allRepository(){}

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..)) ")
    public void allService(){}

    // spring.advanced.advancedspring.payment 하위 패키지이고, 클래스 이름 패턴이 *Repository
    @Pointcut("allPayment() && allRepository()")
    public void paymentAndRepository(){}

    // spring.advanced.advancedspring.payment 하위 패키지이고, 클래스 이름 패턴이 *Service
    @Pointcut("allPayment() && allService()")
    public void paymentAndService(){}
}
