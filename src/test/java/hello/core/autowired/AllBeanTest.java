package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

// 같은 타입으로 선언한 스프링 빈이 모두 필요할 때, Map이나 List로 받아올 수 있다.
// RateDiscountPolicy, FixDiscountPolicy 둘 다 가져오기
public class AllBeanTest {

    @Test
    void findAllBean(){
        //                                                             AutoAppConfig 와 DiscountService에 있는 인스턴스를 빈으로 등록
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);

        // 스프링 빈을 이름으로 가져와서 할인 정책 적용 (fix)
        int fixDiscountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixDiscountPrice).isEqualTo(1000);

        // 스프링 빈을 이름으로 가져와서 할인 정책 적용 (rate)
        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService{
        // map 의 키에 스프링빈의 이름을 넣어주고, 그 값으로 DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies; // DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담음.

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies){
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);// 들어오는 discountCode, 즉 빈 이름으로 스프링 빈을 가져옴
            return discountPolicy.discount(member, price);
        }
    }
}
