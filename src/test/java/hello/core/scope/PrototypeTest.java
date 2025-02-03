package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

//        prototypeBean1.destroy(); // 클라이언트가 직접 종료
//        prototypeBean2.destroy();
        ac.close();
    }

    @Scope("prototype")
//    @Component 생략 가능: AnnotationConfigApplicationContext 에 PrototypeBean 클래스를 넣었으므로
//    클래스 자체가 컴포넌트 스캔의 대상처럼 동작함: 바로 스프링 빈으로 등록
    static class PrototypeBean{

        @PostConstruct
        void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
