package application.aop;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootTest(classes = {SomeClazzForSpringAop.class, PerformanceChecker.class})
@EnableAspectJAutoProxy // 오토프록시
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

    @Autowired
    private SomeClazzForSpringAop someClazz;

    @DisplayName("Spring AOP를 이용한 수행 시간 출력")
    @Test
    void calculatePerformanceTime() {
        assertThat(someClazz.someMethod()).isEqualTo("someMethod를 실행하셨습니다.");
        assertThat(outputStreamCaptor.toString()).contains("수행 시간");
    }
}
