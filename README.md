[블로그 포스팅 주소](https://velog.io/@wish17/%EC%BD%94%EB%93%9C%EC%8A%A4%ED%85%8C%EC%9D%B4%EC%B8%A0-%EB%B0%B1%EC%97%94%EB%93%9C-%EB%B6%80%ED%8A%B8%EC%BA%A0%ED%94%84-36-37%EC%9D%BC%EC%B0%A8-Spring-Core-Spring-Framework%EC%9D%98-%ED%95%B5%EC%8B%AC-%EA%B0%9C%EB%85%90-AOP)

# Section2 - [Spring Core] Spring Framework의 핵심 개념

# AOP

> 관점 지향 프로그래밍 (Aspect-Oriented Programming)

cf. [OOP](https://velog.io/@wish17/TMI-%EC%A0%95%EB%A6%AC#%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8Dobject-oriented-programmingoop)(객체지향 프로그래밍)을 대체하는 것이 아닌 부족한 부분을 보조하는 목적

### 기본 개념

>#### 애스펙트(Aspect)
- 부가 기능을 정의한 코드인 어드바이스(Advice)와 어드바이스를 어디에 적용할지 결정하는 포인트컷(PointCut)을 합친 개념
    -  ``Advice + PointCut ⇒ Aspect``

>#### 핵심 기능(Core Concerns)
- 고유의 기능(업무 로직을 포함)

>#### 부가 기능(CROSS-CUTTING CONCERNS)
- 핵심 기능을 도와주는 부가적인 기능
- ex. 트랜잭션, 보안, 로깅(로그 추적) 코드
- 단독 사용 X, 핵심 기능과 함께 사용

- 관심사의 분리는 **모듈화의 핵심**이다.
    - 비즈니스 클래스에 횡단 관심사와 핵심 관심사가 공존하면 비즈니스(핵심) 코드 파악이 어렵다. (유지보수 어려움)
    - 부가 기능의 불특정 다수 메소드가 반복적으로 구현되어 있으면 유지보수가 어렵다.
    - 따라서 관심사 분리를 통해 모튤화 해야한다.


> 애스팩트(Aspect)
- 여러 객체에 공통으로 적용되는 기능
- 어드바이스 + 포인트컷을 모듈화하여 애플리케이션에 포함되는 횡단 기능
- 여러 ``어드바이스``와 ``포인트컷``이 함께 존재


> 어드바이스(Advice)
- ``조인포인트``에서 수행되는 코드를 의미
- Aspect를 언제 핵심 코드에 적용할 지를 정의한다.
- 시스템 전체 Aspect에 API 호출을 제공
- 부가 기능에 해당 됨.

> **조인 포인트(join point)**
- 애플리케이션 실행 흐름에서의 특정 포인트를 의미
- 조인포인트에 관심 코드(aspect code)를 추가할 수 있다.
(=> 애플리케이션에 새로운 동작을 추가할 수 있다.)
- 횡단 관심은 조인포인트 전/후에 AOP에 의해 자동으로 추가된다.
- 추성적인 개념이고 AOP를 적용할 수 있는 모든 지점이라 생각하면 됨
- 조인 포인트는 항상 메소드 실행 지점으로 제한된다.
    - 스프링 AOP는 프록시 방식을 사용하기 때문
- 어드바이스 적용이 필요한 곳은 애플리케이션 내에 메서드를 갖는다.    


[![](https://velog.velcdn.com/images/wish17/post/636b222d-f746-46fc-a523-213a8e0cd37c/image.png)](https://velog.io/@sum3533279/AOP)

- S는 메서드 실행 전의 포인트이고 E는 메서드 수행 후의 포인트다.
- 이러한 포인트를 어드바이스가 적용될 조인포인트라고 부른다.

> 포인트컷(Pointcut)
- 조인 포인트 중에서 어드바이스가 적용될 위치를 선별하는 기능
- 프록시를 사용하는 스프링 AOP는 메서드 실행 지점만 포인트컷으로 선별 가능

> 위빙(Weaving)
- 포인트컷으로 결정한 타겟의 조인 포인트에 어드바이스를 적용하는 것
    - Advice를 핵심 코드에 적용하는 것을 의미
- 핵심 기능 코드에 영향을 주지 않고 부가 기능을 추가 할 수 있다.

> AOP 프록시(proxy)
- 타겟을 감싸서 타겟의 요청을 대신 받아주는 랩핑(Wrapping) 오브젝트
- AOP 기능을 구현하기 위해 만든 프록시 객체
- 스프링에서 AOP 프록시는 JDK 동적 프록시 또는 CGLIB 프록시다.

> 타겟 (Target)
- 부가기능을 부여할 대상
- 핵심 기능을 담고 있는 모듈
- Adivce를 받는 객체이고 포인트컷으로 결정된다.

> 어드바이저(Advisor)
- 하나의 ``어드바이스``와 하나의 ``포인트 컷``으로 구성된 것


## 타입별 Advice

어드바이스는 기본적으로 순서를 보장하지 않는다.

- 순서를 지정하고 싶으면 ``@Aspect`` 적용 단위로 ``org.springframework.core.annotation.@Order`` 애너테이션을 적용해야 함
    - 어드바이스 단위가 아니라 클래스 단위로 적용할 수 있음
    - 하나의 애스펙트에 여러 어드바이스가 존재하면 순서를 보장 받을 수 없음
    
    
### Advice 종류

#### Before

- 조인 포인트 실행 이전에 실행
- 타겟 메서드가 실행되기 전에 처리해야할 필요가 있는 부가 기능을 타겟 메서드 호출 전에 실행한다.
- 일반적으로 리턴타입이 void
    - 리턴 값을 갖더라도 실제 Advice 적용 과정에 아무 영향이 없다.
- !!주의!! 메서드에서 예외를 발생시킬 경우 대상 객체의 메서드가 호출되지 않게 된다.    

```java
@Before("hello.aop.order.aop.Pointcuts.orderAndService()")
public void doBefore(JoinPoint joinPoint) {
    log.info("[before] {}", joinPoint.getSignature());
}
```
	- 작업 흐름을 변경할 수 없다.
    - 메서드 종료 시 자동으로 다음 타겟이 호출된다.

#### After returning

- 조인 포인트가 정상 완료 후 실행
- 메서드가 예외 없이 실행된 이후에 공통 기능을 실행

```java
@AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
public void doReturn(JoinPoint joinPoint, Object result) {
    log.info("[return] {} return={}", joinPoint.getSignature(), result);
}
```
	- returning 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
    - returning 절에 지정된 타입의 값을 반환하는 메서드만 대상을 실행된다.


#### After throwing

- 메서드가 예외를 던지는 경우에 실행
- 메서드를 실행하는 도중 예외가 발생한 경우 공통 기능을 실행

```java
@AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
public void doThrowing(JoinPoint joinPoint, Exception ex) {
    log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
}
```
	- throwing 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
    - throwing 절에 지정된 타입과 맞는 예외를 대상으로 실행

#### After (finally)

- 조인 포인트의 동작(정상 또는 예외)과는 상관없이 실행
    - 자바 예외처리 finally같은거임
- 메서드 실행 후 공통 기능을 실행
- 일반적으로 리소스를 해제하는데 사용


#### Around

- 메서드 호출 전후에 수행하며 가장 강력한 어드바이스다.
    - 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등이 가능
- 어드바이스의 첫 번째 파라미터는 ProceedingJoinPoint를 사용해야 한다.
- proceed()를 통해 대상을 실행
    - proceed()를 여러번 실행 가능


**@Around만 있어도 모든 기능 수행이 가능**하다. (매우 강력)
(타겟 등 고려해야할 사항이 있을 때 정상적으로 작동이 되지 않는 경우 있음)

> cf. 
- @Before, @After와 같은 어드바이스는 기능은 적지만 원하는대로 작동되고 코드가 단순하다.
- 각 애너테이션만 봐도 타겟 실행 전에 어떤 일을 하는지 명확하게 알 수 있다.
- @Around만 사용해서 모두 해결하는 것보다 제약을 가지더라도 실수를 미연에 방지하는게 더 좋다.
- 제약을 두면 문제 자체가 발생하지 않게 하며, 역할이 명확해진다.


## Pointcut 표현식

포인트컷은 관심 조인 포인트를 결정하므로 어드바이스가 실행되는 시기를 제어할 수 있다.

```java
@Pointcut("execution(* transfer(..))") // 포인트컷 표현식
private void anyOldTransfer() {} // 포인트컷 서명
```


### 포인트컷 지시자

포인트컷 표현식은 execution 같은 포인트컷 지시자(Pointcut Designator, PCD)로 시작한다.

#### 포인트컷 지시자 종류

<table><thead><tr><th>종류</th><th>설명</th></tr></thead><tbody><tr><td>execution</td><td>메서드 실행 조인트 포인트를 매칭한다. <br> 스프링 AOP에서 가장 많이 사용하며, 기능도 복잡하다.</td></tr><tr><td>within</td><td>특정 타입 내의 조인 포인트를 매칭한다.</td></tr><tr><td>args</td><td>인자가 주어진 타입의 인스턴스인 조인 포인트</td></tr><tr><td>this</td><td>스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트</td></tr><tr><td>target</td><td>Target 객체(스프링 AOP 프록시가 가르키는 실제 대상)를 대상으로 하는 조인 포인트</td></tr><tr><td>@target</td><td>실행 객체의 클래스에 주어진 타입의 애너테이션이 있는 조인 포인트</td></tr><tr><td>@within</td><td>주어진 애너테이션이 있는 타입 내 조인 포인트</td></tr><tr><td>@annotation</td><td>메서드가 주어니 애너테이션을 가지고 있는 조인 포인트를 매칭</td></tr><tr><td>@args</td><td>전달된 실제 인수의 런타임 타입이 주어진 타입의 애너테이션을 갖는 조인 포인트</td></tr><tr><td>bean</td><td>스프링 전용 포인트컷 지시자이고 빈의 이름으로 포인트컷을 지정한다.</td></tr></tbody></table>


``execution``을 주로 사용하고 나머지는 거의 사용 안함.


### Pointcut 표현식 결합

- 포인트컷 표현식은 &&, ||, ! 를 사용하여 결합할 수 있다.

- 이름으로 pointcut 표현식을 참조할 수도 있다.

```java
@Pointcut("execution(public * *(..))")
private void anyPublicOperation() {} // 메서드 실행 조인 포인트가 공용 메서드의 실행을 나타내는 경우 일치

@Pointcut("within(com.xyz.myapp.trading..*)")
private void inTrading() {} // 메서드 실행이 거래 모듈에 있는 경우에 일치

@Pointcut("anyPublicOperation() && inTrading()")
private void tradingOperation() {} // 메서드 실행이 거래 모듈의 공개 메서드를 나타내는 경우 일치
```

#### 일반적인 pointcut 표현식들

- execution

```java
// 모든 공개 메서드 실행
execution(public * *(..))

// set 다음 이름으로 시작하는 모든 메서드 실행
execution(* set*(..))

// AccountService 인터페이스에 의해 정의된 모든 메서드의 실행
execution(* com.xyz.service.AccountService.*(..))

// service 패키지에 정의된 메서드 실행
execution(* com.xyz.service.*.*(..))

// 서비스 패키지 또는 해당 하위 패키지 중 하나에 정의된 메서드 실행
execution(* com.xyz.service..*.*(..))

```
- 그 외 나머지

```java
//서비스 패키지 내의 모든 조인 포인트 (Spring AOP에서만 메서드 실행)
within(com.xyz.service.*)

//서비스 패키지 또는 하위 패키지 중 하나 내의 모든 조인 포인트 (Spring AOP에서만 메서드 실행)
within(com.xyz.service..*)

//AccountService 프록시가 인터페이스를 구현하는 모든 조인 포인트 (Spring AOP에서만 메서드 실행)
this(com.xyz.service.AccountService)

//AccountService 대상 객체가 인터페이스를 구현하는 모든 조인 포인트 (Spring AOP에서만 메서드 실행)
target(com.xyz.service.AccountService)

//단일 매개변수를 사용하고 런타임에 전달된 인수가 Serializable과 같은 모든 조인 포인트 (Spring AOP에서만 메소드 실행)
args(java.io.Serializable)

//대상 객체에 @Transactional 애너테이션이 있는 모든 조인 포인트 (Spring AOP에서만 메서드 실행)
@target(org.springframework.transaction.annotation.Transactional)

//실행 메서드에 @Transactional 애너테이션이 있는 조인 포인트 (Spring AOP에서만 메서드 실행)
@annotation(org.springframework.transaction.annotation.Transactional)

//단일 매개 변수를 사용하고 전달된 인수의 런타임 유형이 @Classified 애너테이션을 갖는 조인 포인트(Spring AOP에서만 메서드 실행)
@args(com.xyz.security.Classified)

//tradeService 라는 이름을 가진 스프링 빈의 모든 조인 포인트 (Spring AOP에서만 메서드 실행)
bean(tradeService)

//와일드 표현식 *Service 라는 이름을 가진 스프링 빈의 모든 조인 포인트
bean(*Service)
```

## JoinPoint

> 조인 포인트는 추상적인 개념이고, AOP를 적용할 수 있는 지점을 의미

- 프록시를 사용하는 스프링 AOP
    - 조인 포인트가 **메서드 실행 지점으로 제한 됨**
    - 생성자나 static 메서드, 필드 값 접근에는 프록시 개념이 적용될 수 없음
    - 스프링 컨테이너가 관리할 수 있는 스프링 빈에만 AOP를 적용할수 있다.

- AspectJ를 사용하는 AOP는 모든 지점 적용 가능


- JoinPoint 메소드는 어드바이스의 종류에 따라 사용방법이 다소 다르지만 기본적으로 어드바이스 메소드에 매개변수로 선언만 하면 된다.



### JoinPoint 인터페이스의 주요 기능

``JoinPoint.getArgs()``
- JoinPoint에 전달된 인자를 배열로 반환

``JoinPoint.getThis()``
- AOP 프록시 객체를 반환

``JoinPoint.getTarget()`` 
- AOP가 적용된 대상 객체를 반환
    - 클라이언트가 호출한 비즈니스 메소드를 포함하는 비즈니스 객체를 반환

``JoinPoint.getSignature() ``
- 조인되는 메서드에 대한 설명을 반환
    - 클라이언트가 호출한 메소드의 시그니처(리턴타입, 이름, 매개변수) 정보가 저장된 [Signature](https://velog.io/@wish17/TMI-%EC%A0%95%EB%A6%AC#signature) 객체를 반환
    
``JoinPoint.toString() ``
- 조인되는 방법에 대한 유용한 설명을 인쇄


``proceed() ``
- 다음 어드바이스나 타켓을 호출

## 애너테이션(Annotation)을 이용한 AOP


### @AspectJ 지원

> ``@AspectJ``
애너테이션이 있는 일반 Java 클래스로 관점을 선언하는 스타일

#### @AspectJ 지원 활성화

``@Configuration``으로 ``@AspectJ`` 지원을 활성화하려면 ``@EnableAspectJAutoProxy`` 애너테이션을 추가하면 된다.

```java
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
}
```

@AspectJ 지원이 활성화되면 @AspectJ 관점(@Aspect 애너테이션)이 있는 클래스로 애플리케이션 컨텍스트에 정의된 모든 빈이 Spring에서 자동으로 감지되고 Spring AOP를 구성하는 데 사용된다.

```java
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class NotVeryUsefulAspect {
}
```

pointcut 표현식은 ``@Pointcut`` 어노테이션을 사용하여 표시

***

## AOP실습 오류

test파일의 경로를 잘못 설정해서

``Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test`` 오류가 났었다.

[참조](https://jjunii486.tistory.com/172)

아래과 같이 경로를 일치시켜 해결
![](https://velog.velcdn.com/images/wish17/post/698f3a87-4b2e-4e2b-bc02-0d77dbdc7764/image.png)

실습내용은 [깃허브](https://github.com/wish9/AOP-practice1)에서 확인
