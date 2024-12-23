package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


// 컴포넌트 스캔: @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록.
// @Configuration 애노테이션도 @Component 애노테이션이 붙어있기 때문에 컴포넌트 스캔의 대상이 됨.
// excludeFilters: 이전에 @Configuration 으로 등록된 TestConfig, AppConfig 가 컴포넌트 스캔의 대상이 되므로, 제외시켜줌.
// MemoryMemberRepository, RateDiscountPolicy, MemberServiceImpl에 @Component 붙여준당
// 컴포넌트 스캔 만으로는 의존관계 주입이 불가능하므로 @Autowired 라는 애노테이션을 사용(자동 의존관계 주입): ~ServiceImpl에서 사용
@Configuration
@ComponentScan(
//        basePackages = "hello.core", //basePackage: 탐색 위치 지정. member 패키지만 스캔.
//        basePackageClasses : 지정한 클래스의 패키지를 탐색 시작 위치로 지정. 지정하지 않으면 @Configuration 이 붙은 클래스의 패키지가 시작위치.
        excludeFilters =  @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // 빈 이름 중복. 빈 이름 중복 시 자동 등록 빈 보다 수동 등록 빈이 우선적으로 등록됨.
    // 수동 빈이 자동 빈을 오버라이딩해버린다. -> 현재는 스프링 부트가 오류를 내도록 수정됨.
/*    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }*/
}
