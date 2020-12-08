package springbook.learningtest.junit;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(JunitTest.class)
public class JunitTest {

    @Autowired
    ApplicationContext context;

    private static final Set<JunitTest> testObjects = new HashSet<>();

    private static ApplicationContext contextObject = null;


    @DisplayName("테스트를 실행할 때마다 항상 새로운 테스트 객체(JunitTest)를 생성한다.")
    @Test
    void test1() {
        assertThat(testObjects).isNotIn(this);
        testObjects.add(this);
    }

    @DisplayName("테스트를 실행할 때마다 항상 새로운 테스트 객체(JunitTest)를 생성한다.")
    @Test
    void test2() {
        assertThat(testObjects).isNotIn(this);
        testObjects.add(this);
    }

    @DisplayName("ApplicationContext가 null이라면 첫 번째 테스트")
    @Test
    void test3() {
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @DisplayName("두번째 테스트부터는 아예 같은 ApplicationContext이다")
    @Test
    void test4() {
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

}
