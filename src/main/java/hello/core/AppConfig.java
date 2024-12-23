package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 구현 클래스가 직접 객체(구현체)를 선택하는 것은 배우가 배역을 선택하는 것과 같다.
 즉, 로미오 역할을 맡은 배우가 줄리엣 역할을 맡을 배우를 선택하는 것.
 배우는 너무 많은 책임을 지게 되고, 별도의 기획자가 필요하다. 애플리케이션도 마찬가지. (책임 분리)
 애플리케이션의 전체 동작 방식을 구성하기 위해 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스 */
// 스프링으로 전환: @Configuration, @Bean 을 붙여줌
@Configuration // Application의 설정/구성 정보를 담당함. 이 어노테이션이 없어도 bean을 생성할 수 있지만 싱글톤 보장 X
public class  AppConfig {

    // memberService, orderService 빈을 만들다보면 repository의 생성자를 두 번 호출하는 것인데, 싱글톤 패턴이 유지될까??
    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository()

    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository
    // 빈 생성 코드를 보면 memberRepository 가 세 번 호출될 것 같지만 한 번만 호출됨.

     /*
     리팩토링: 역할이 더 잘 드러나도록 리팩토링
     new Class() 를 메소드로 추출: 어떤 '역할'이 존재하고, 역할에 어떤 구현체를 넣을 지 한 눈에 보인다.
     예시: new MemberServiceImpl(new MemoryMemberRepository()); 에서
     Repository 는 역할에 해당하므로 메소드로 추출해준다
            결과: new MemberServiceImpl(memberRepository());
     >> 역할을 명확하게 한 눈에 볼 수 있을 뿐만 아니라 역할에 사용되는 구현체가 바뀔 때 코드 수정도 줄어든다. */
    @Bean // 스프링 컨테이너에 등록
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    // public static~ 에서 public~ 으로 변경
    // @Configuration 내부의 @Bean 메소드에 static이 사용되면 싱글톤 패턴이 적용되지 않음.
    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return new OrderServiceImpl();
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
