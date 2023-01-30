package spring.advanced.advancedspring.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6Advice {

//    /**
//     * 메소드 호출 전후에 수행
//     * 가장 강력한 어드바이스, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등 가능
//     */
//    @Around("spring.advanced.advancedspring.order.aop.PointCuts.orderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            // @Before
//            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//            Object result = joinPoint.proceed(); // 조인 포인트
//            // @AfterReturning
//            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
//            return result;
//        } catch (Exception e) {
//            // @AfterThrowing
//            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//            throw e;
//        } finally {
//            // @After
//            log.info("[트랜잭션 릴리즈] {}", joinPoint.getSignature());
//        }
//    }

    /**
     * 조인 포인트 실행 이전에 실행
     */
    @Before("spring.advanced.advancedspring.order.aop.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
        // 조인포인트는 자동으로 실행
    }

    /**
     * 조인 포인트가 정상 완료후 실행
     */
    @AfterReturning(value = "spring.advanced.advancedspring.order.aop.PointCuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    /**
     * 메소드가 예외를 던지는 경우 실행
     */
    @AfterThrowing(value = "spring.advanced.advancedspring.order.aop.PointCuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
        // throw e 자동으로 실행
    }

    /**
     * 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)
     */
    @After("spring.advanced.advancedspring.order.aop.PointCuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }





}
