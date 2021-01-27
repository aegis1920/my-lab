package com.bingbong.effectivejava.item19;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @DisplayName("상위 클래스 생성자에 override한 메서드가 있을 경우")
    @Test
    void overrideMethodInSuperClass() {
        // Sub를 생성하자마자 Super의 생성자가 실행된다
        // 초기화되지 않았기에 null이 출력된다.
        Sub sub = new Sub();
        // HelloWorld가 출력된다.
        sub.overrideMe();

        assertThat(outContent.toString()).isEqualTo("null" + System.lineSeparator() + "Hello World!" + System.lineSeparator());
    }
}
