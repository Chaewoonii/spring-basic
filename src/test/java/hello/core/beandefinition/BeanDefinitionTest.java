package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {

    /*
   - ApplicationContext 가 아니라 AnnotationConfig~로 객체를 생성한 이유는 ApplicationContext 는 getBeanDefinition() 메서드를 못 써서 임. (GenericXml 도 마찬가지)

   - GenericXml 로 메타정보를 확인하면 bean: class [hello.core.member.MemberServiceImpl]와 같이 클래스가 직접적으로 드러남.
     xml 파일에서 빈을 직접 설정하여 스프링 컨테이너에 등록하기 때문에, 클래스 설정 정보가 담겨있기 때문이다. (그리고 factoryBeanName, factoryMethodName 이 null 임)

   - AnnotationConfig 로 메타정보를 확인하면 bean: class [null]이고 factoryBeanName=AppConfig, factoryMethodName=memberService 이다.
     자바 코드를 통해 스프링 빈을 등록하려면 FactoryMethod 방식을 통해야 하기 때문이다. (AppConfig 라는 Factory 의 memberService() 라는 메서드를 호출해서 빈을 생성)
     메서드를 호출하여 빈을 생성하기 때문에 직접적으로 bean class 가 설정되어있지 않고, factory 가 설정된다.

     */
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for(String beanDefinitionName : beanDefinitionNames){
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefintionName = " + beanDefinitionName +
                        " beanDefinition = " + beanDefinition);
            }
        }
    }
}
