package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 객체 생성부(new~)를 없애고 생성자를 통해 주입받음
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discoutnPrice = discountPolicy.discount(member, itemPrice);
        // 단일책임의 원칙(SRP)을 잘 지킨 경우.
        // 설계가 잘 됨: OrderService는 할인 정책은 모르고, 결과만 받음
        // 할인 정책은 DiscountPolicy가 처리. -> 정책 수정 시 DiscountPolicy만 수정하면 됨.

        return new Order(memberId, itemName, itemPrice, discoutnPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
