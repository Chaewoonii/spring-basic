package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

// InitializingBean, DisposableBean 상속 전에는 connect 가 null
// 객체 생성 단계에서(생성자서) connect 와 call 이 이루어졌기 때문. (setUrl을 통하여 url이 초기화되기 전에 생성 단계에서 "연결 먼저" 수행됨)
// afterPropertiesSet, destroy 를 통해 생성과 초기화를 분리: 객체 생성 후 초기화(setter 호출), 네트워크 연결
// 요즘엔 잘 사용하지 않는다... : 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없음
//public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메시지");
    }

    public void setUrl(String url){
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect(){
        System.out.println("connect: " + url);
    }

    public void call(String message){
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect(){
        System.out.println("close " + url);
    }

    // 의존관계 주입이 끝나면 호출
    @PostConstruct // 최신 스프링 권장
    public void init() throws Exception {
        // 기존에 생성자에 포함되었던 객체 초기화 부분을 가져온다. 생성과 초기화는 분리하는 것이 유지보수 측면에서 좋음.
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    // 객체 소멸 시 호출
    @PreDestroy // 최신 스프링 권장
    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
