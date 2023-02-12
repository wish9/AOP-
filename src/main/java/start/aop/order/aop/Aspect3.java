package start.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class Aspect3 { // 트랜잭션이 동작하는 것처럼 로그 남기는 기능(어드바이스)을 추가

    @Pointcut("execution(* start.aop.order..*(..))")
    private void allOrder(){}

    @Pointcut("execution(* *..*Service.*(..))") // 이름이 Service로 끝나는 것을 대상으로 함 (클래스, 인터페이스에 모두 적용)
    private void allService(){}

    @Around("allOrder()") // OrderService와 OrderRepository 두 클래스 모두에 적용 됨
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("log -> {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("allOrder() && allService()") // OrderService 클래스에만 적용 됨
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            log.info("트랜잭션 시작 -> {}", joinPoint.getSignature()); // 핵심 로직 실행 직전에 트랜잭션 시작
            Object result = joinPoint.proceed(); // 핵심 로직 실행
            log.info("트랜잭션 커밋 -> {}", joinPoint.getSignature()); // 핵심 로직 실행에 문제가 없으면 커밋(Commit)=> 변경 사항을 데이터베이스에 적용
            return result;
        } catch (Exception e) { // 핵심 로직 실행에 예외가 있으면
            log.info("트랜잭션 롤백 -> {}", joinPoint.getSignature()); // 롤백(Rollback) : 변경 사항 적용하지 않고 이전 상태로 되돌아가기
            throw e;
        } finally {
            log.info("리소스 릴리즈 -> {}", joinPoint.getSignature());
        }
    }
}
