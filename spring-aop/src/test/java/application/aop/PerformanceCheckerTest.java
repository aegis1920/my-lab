package application.aop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootTest(classes = {SomeClazz.class, PerformanceChecker.class})
@EnableAspectJAutoProxy // 오토프록싱
public class PerformanceCheckerTest {

    @Autowired
    private SomeClazz someClazz;

    @Test
    void performance() {
        assertThat(someClazz.someMethod()).isEqualTo("someMethod를 실행하셨습니다.");
    }
}
