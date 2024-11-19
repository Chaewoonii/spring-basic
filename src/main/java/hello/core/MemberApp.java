package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {

/*      MemberService memberService = new MemberServiceImpl();
        //AppConfig가 객체 생성을 관리.
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();*/

         /*ApplicationContext: 스프링 컨테이너
         AppConfig 가 어노테이션을 기반으로 설정하고 있기 때문에 AnnotationConfigApplicationContext 객체를 생성하고, AppConfig 를 주입
         -> 스프링이 컨테이너에 @Bean 을 넣어 관리*/
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // ApplicationContext("메서드명", 반환타입)
        // 메서드명: 스프링 빈은 스프링 컨테이너에 "메서드 이름"으로 저장된다. MemberService 객체를 생성하는 메서드가 memberService() 이므로 memberService 를 넣어준다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("findMember = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
