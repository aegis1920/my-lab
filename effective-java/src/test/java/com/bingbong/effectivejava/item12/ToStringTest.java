package com.bingbong.effectivejava.item12;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToStringTest {

    // Object 클래스의 toString 메서드를 호출했을 때
    private static final Pattern OBJECT_PATTERN = Pattern.compile("(.+)PhoneNumberByDefault@(.+)");

    // Override한 toString 메서드를 불렀을 때
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("PhoneNumber : \\{ \\d{3}-?\\d{4}-?\\d{4} \\}");

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @DisplayName("Object에 있는 기본 toString")
    @Test
    void callToString_default() {
        PhoneNumberByDefault phoneNumber = new PhoneNumberByDefault("010", "1234", "1234");

        assertThat(OBJECT_PATTERN.matcher(phoneNumber.toString()).matches()).isTrue();
    }

    @DisplayName("System.out.println 메서드에서 객체 호출")
    @Test
    void callToString_Println() {
        PhoneNumberByDefault phoneNumber = new PhoneNumberByDefault("010", "1234", "1234");
        System.out.println(phoneNumber);

        assertThat(OBJECT_PATTERN.matcher(outContent.toString()).find()).isTrue();
    }

    @DisplayName("System.out.print 메서드에서 객체 호출")
    @Test
    void callToString_Print() {
        PhoneNumberByDefault phoneNumber = new PhoneNumberByDefault("010", "1234", "1234");
        System.out.print(phoneNumber);

        assertThat(OBJECT_PATTERN.matcher(outContent.toString()).find()).isTrue();
    }


    @DisplayName("객체에 문자열을 더했을 때")
    @Test
    void callToString_PlusString() {
        PhoneNumberByDefault phoneNumber = new PhoneNumberByDefault("010", "1234", "1234");

        assertThat(OBJECT_PATTERN.matcher(phoneNumber + "").matches()).isTrue();
    }

    @DisplayName("assert 구문을 사용했을 때")
    @Test
    void callToString_Assert() {
        PhoneNumberByDefault phoneNumber = new PhoneNumberByDefault("010", "1234", "1234");

        System.out.print(phoneNumber);
        assertThatCode(() -> {
            assert OBJECT_PATTERN.matcher(phoneNumber.toString()).matches();
        }).doesNotThrowAnyException();
    }

    @DisplayName("toString을 Override했을 때")
    @Test
    void callToString_OverrideToString() {
        PhoneNumberByOverrideToString phoneNumber = new PhoneNumberByOverrideToString("010", "1234",
            "1234");

        assertThat(PHONE_NUMBER_PATTERN.matcher(phoneNumber.toString()).matches()).isTrue();
    }
}
