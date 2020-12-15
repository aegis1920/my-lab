package application.jdkdynamicproxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PerformanceCheckerTest {

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
    void create_UsingJdkDynamicProxy() {
        MemberService memberService = new MemberServiceImpl();
        Class<? extends MemberService> aClass = memberService.getClass();
        MemberService proxy = (MemberService) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new PerformanceChecker(memberService));

        assertThat(proxy.create("bingbong").getName()).isEqualTo("bingbong");
        assertThat(outputStreamCaptor.toString()).contains("수행 시간");
    }

    @DisplayName("Proxy를 사용하지 않은 경우 수행 시간을 출력하지 않음")
    @Test
    void create_NotUsingJdkDynamicProxy() {
        MemberService memberService = new MemberServiceImpl();

        assertThat(memberService.create("bingbong").getName()).isEqualTo("bingbong");
        assertThat(outputStreamCaptor.toString()).doesNotContain("수행 시간");
    }
}
