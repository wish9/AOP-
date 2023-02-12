package start.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts { // 포인트컷 모아두는 클래스
    @Pointcut("execution(* start.aop.order..*(..))")
    public void allOrder(){}

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
