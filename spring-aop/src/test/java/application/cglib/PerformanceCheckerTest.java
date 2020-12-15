package application.cglib;

import static org.assertj.core.api.Assertions.assertThat;

import application.cgllibproxy.MemberServiceImpl;
import application.cgllibproxy.PerformanceChecker;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

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

    @DisplayName("cglib을 이용한 수행 시간 출력")
    @Test
    void create() {
        MemberServiceImpl memberService = (MemberServiceImpl) Enhancer
            .create(MemberServiceImpl.class, new PerformanceChecker());

        assertThat(memberService.create("bingbong").getName()).isEqualTo("bingbong");
        assertThat(outputStreamCaptor.toString()).contains("수행 시간");
    }
}
