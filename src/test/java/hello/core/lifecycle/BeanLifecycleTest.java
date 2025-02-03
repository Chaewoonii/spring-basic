package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifecycleTest {

    @Test
    public void lifeCycleTest(){
        // ApplicationContext 는 NetworkClient.close() 사용 불가
        // ConfigurableApplicationContext 는 AnnotationConfigApplicationContext 의 상위 인터페이스
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{

        // 초기화 및 종료 메서드 적용. 외부 라이브러리에도 적용 가능하다
        // 스프링 빈의 종료 메서드(destroyMethod)는 inferred, "추론" 이다. close 나 shutdown 이라는 이름의 메서드를 자동으로 호출해준다.
        @Bean //(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
