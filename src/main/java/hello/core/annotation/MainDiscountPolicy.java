package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

// @Qualifier 에서 긁어온다. @MainDiscountPolicy 를 사용하면 아래 어노테이션의 기능이 모두 동작한다
// mainDiscountPolicy 를 직접 타이핑하는 것은 컴파일 시 타입 체크가 안된다.(문자열을 틀리게 작성해도 컴파일 오류가 발생하지 않음.)
// 따라서, 직접 어노테이션을 만들어서 문제를 해결할 수 있다
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}
