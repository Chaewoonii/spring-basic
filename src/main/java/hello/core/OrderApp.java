package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;

public class OrderApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl(null);
//        OrderService orderService = new OrderServiceImpl(null, null);

        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 20000);

        // order 객체가 toString으로 출력됨.
        System.out.println("order = " + order);
//        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
