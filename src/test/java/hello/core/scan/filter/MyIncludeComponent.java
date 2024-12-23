package hello.core.scan.filter;

import java.lang.annotation.*;

// @Component 에서 긁어옴
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {

}
