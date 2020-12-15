package application.proxypattern;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceImplProxyTest {

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

    @DisplayName("프록시 패턴을 이용한 수행 시간 출력")
    @Test
    void create() {
        MemberService memberService = new MemberServiceImplProxy();

        assertThat(memberService.create("bingbong").getName()).isEqualTo("bingbong");
        assertThat(outputStreamCaptor.toString()).contains("수행 시간");
    }
}
