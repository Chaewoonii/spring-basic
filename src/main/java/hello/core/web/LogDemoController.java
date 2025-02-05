package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LonDemoService logDemoService;
    private final MyLogger myLogger; // DL: Dependency Lookup

    /*
    * 오류 발생: Scope 'request' is not active~
    * MyLogger 의 스코프는 http 요청이 들어오고 나갈 때 까지이다.
    * 그런데, 스프링이 올라갈 때 의존관계 주입을 위해 MyLogger가 필요하다
    * http 요청이 들어오지도 않았는데 (빈이 생성되지도 않았는데), MyLogger를 요청하니 오류가 발생한다.
    * ----> Provider 로 해결!!
    *       -> @Scope 에서 proxyMode = ScopedProxyMode.TARGET_CLASS 를 활용하여 코드라인 줄임
    *       -> 클래스면 TARGET_CLASS, 인터페이스면 INTERFACES 를 선택
    *       -> MyLogger 의 가짜 프록시 클래스를 만들어두고 Http request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해둘 수 있다.
    * ----> 스프링 인터셉터나 서블릿 필터를 활용해도 좋다
    * */

    @SneakyThrows
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
        System.out.println("myLogger = " + myLogger.getClass()); // SpringCGLIB: 가짜 프록시 클래스
        myLogger.setRequestURL(requestURL); // myLogger 의 기능을 사용해야할 때 진짜 MyLogger 클래스를 찾아서 동작. 마치 provider 처럼 동작한다.

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }



}
