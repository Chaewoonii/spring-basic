package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autoWiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);


    }

    static class TestBean{

        // Member 클래스는 스프링 컨테이너에서 관리되는 빈이 아님
        // @Autowired(required = false): 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안 됨
        // 따라서, setNoBean1은 호출 안됨
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1); // 미출력. 호출 X
        }

        // @Nullable: 자동 주입할 대상이 없으면 null 이 입력됨
        @Autowired
        public void setNobean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2); // noBean2 = null
        }

        // 자동 주입할 대상이 없으면 Optional.empty 가 입력됨
        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3); // noBean3 = Optional.empty
        }
    }
}
