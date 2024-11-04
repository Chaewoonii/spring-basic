package hello.core;

import hello.core.member.*;

public class MemberApp {
    public static void main(String[] args) {

//        MemberService memberService = new MemberServiceImpl();
        // AppConfig가 객체 생성을 관리.
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("findMember = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
