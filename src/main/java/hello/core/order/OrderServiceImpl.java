package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // 필수 값을 파라미터로 받는 생성자 만들어줌
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

/*    필드 주입 패턴
    - 객체 생성부(new~)를 없애고 생성자를 통해 주입받음
    - final: 값이 반드시 있어야 함을 의미. 생성자에 값을 채워넣어야 함
    - 값을 변경할 수 없어 테스트가 어려움(테스트 시 더미로 memberRepository 등을 생성하여 넣을 수 없고, 값이 고정됨.)
    - 필드 주입 패턴은 테스트코드에서 인스턴스를 새로 생성해서 사용할 때 많이 이용.
    - OrderServiceTest, fieldInjectionTest 참고
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;
 */
    /*
    일반 메서드 주입. 한 번에 여러 필드를 주입받을 수 있음. 일반적으로 잘 사용하지 않음
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;
    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

     */

    //    생성자 주입 패턴
    // final 키워드: "상수"이므로 값이 불변, 인스턴스 생성 시 반드시 값을 주입하도록 강제
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

/*     @Qualifier
     - 사용방식: 구현 클래스에 @Qualifier 어노테이션을 붙이고, 이름을 준다 -> @Qualifier("mainDiscountPolicy")
                호출부(생성자) 파라미터에 @Qualifier 어노테이션을 붙인다
                예시:  public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy){...}
     - DiscountPolicy 는 구현체가 Fix, Rate 두개이다. 둘 다 @Component 를 붙여 Bean으로 만들면,
     - 스프링 컨테이너가 관리하는 빈 중 DiscountPolicy 타입이 두 개 이므로 어떤 것을 주입시켜야할지 알 수 없다 : Error 발생
     - @Qualifier 를 통해 어느 구현체를 주입할지 결정할 수 있다.
     */
    @Autowired // 생성자가 딱 1개만 있으면 @Autowired 생략가능. 의존관계 자동 주입
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
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

    // 테스트용 | lombok, @Getter로 대체
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
