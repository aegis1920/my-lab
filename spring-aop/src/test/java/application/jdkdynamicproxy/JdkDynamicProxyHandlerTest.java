package application.jdkdynamicproxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JdkDynamicProxyHandlerTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @DisplayName("JDK Dynamic Proxy를 이용한 수행 시간 출력")
    @Test
    void invoke_UsingJdkDynamicProxy() {
        JdkDynamicProxy object = new SomeClazzForJdkDynamicProxy();
        Class<? extends JdkDynamicProxy> aClass = object.getClass();
        JdkDynamicProxy proxy = (JdkDynamicProxy) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new JdkDynamicProxyHandler(object));

        assertThat(proxy.someMethod()).isEqualTo("someMethod를 실행하셨습니다.");
        assertThat(outputStreamCaptor.toString()).contains("수행 시간");
    }

    @DisplayName("Proxy를 사용하지 않은 경우")
    @Test
    void invoke_NotUsingJdkDynamicProxy() {
        JdkDynamicProxy object = new SomeClazzForJdkDynamicProxy();

        assertThat(object.someMethod()).isEqualTo("someMethod를 실행하셨습니다.");
        assertThat(outputStreamCaptor.toString()).doesNotContain("수행 시간");
    }
}
