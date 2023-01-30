package spring.advanced.advancedspring.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* spring.advanced.advancedspring.order..*(..))")
    public void allOrder(){}

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    // allOrder() 포인트컷와 allService() 포인트컷을 조합해서 새로운 포인트컷
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
