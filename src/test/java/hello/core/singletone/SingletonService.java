package hello.core.singletone;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }

    // 생성자를 외부에서 호출할 수 없도록 막음.
    private SingletonService(){}

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
