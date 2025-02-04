package hello.core.singletone;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A 사용자 10000 주문
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB: B 사용자 20000 주문
        int userBPrice = statefulService2.order("userB", 20000);

        // ThreadA: 사용자 A 주문금액 조회
//        int price = statefulService1.getPrice();
//        System.out.println("price = " + price); // 20000: price가 B 사용자의 가격으로 저장됨
        System.out.println("userAPrice = " + userAPrice);
//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}
