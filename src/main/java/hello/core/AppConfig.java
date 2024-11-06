package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 구현 클래스가 직접 객체(구현체)를 선택하는 것은 배우가 배역을 선택하는 것과 같다.
// 즉, 로미오 역할을 맡은 배우가 줄리엣 역할을 맡을 배우를 선택하는 것.
// 배우는 너무 많은 책임을 지게 되고, 별도의 기획자가 필요하다. 애플리케이션도 마찬가지. (책임 분리)
// 애플리케이션의 전체 동작 방식을 구성하기 위해 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스
public class AppConfig {

    // 리팩토링: 역할이 더 잘 드러나도록 리팩토링
    // new Class() 를 메소드로 추출: 어떤 '역할'이 존재하고, 역할에 어떤 구현체를 넣을 지 한 눈에 보인다.
    // 예시: new MemberServiceImpl(new MemoryMemberRepository()); 에서
    // Repository 는 역할에 해당하므로 메소드로 추출해준다
    // 결과: new MemberServiceImpl(memberRepository());
    // >> 역할을 명확하게 한 눈에 볼 수 있을 뿐만 아니라 역할에 사용되는 구현체가 바뀔 때 코드 수정도 줄어든다.
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    private static MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
