package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder(){
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    /*
    @Test
    void fieldInjectionTest(){
        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000);
        // 자동 의존관계 주입: 필드 주입 방식 선택 시 NPE 터짐 -> setter 를 열어야 함 -> 수정자 주입 패턴으로 변경...
        // 변경 가능성이 있음. 결국 요즘 잘 안씀...

        orderService.setMemberRepository(new MemoryMemberRepository());
        orderService.setDiscountPolicy(new FixDiscountPolicy());
        orderService.createOrder(1L, "itemA", 10000);
    }*/
}
