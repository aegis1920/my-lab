package com.bingbong.effectivejava.item4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CustomStringUtilsTest {

    @DisplayName("유틸리티 클래스 생성 실패 - 예외 처리")
    @Test
    void constructor() throws Exception {
        Constructor<CustomStringUtils> constructor = CustomStringUtils.class
            .getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
            .isInstanceOf(InvocationTargetException.class)
            .getCause()
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("공백 체크, Null 또는 공백 - True 반환")
    @ParameterizedTest
    @NullAndEmptySource
    void isBlank_ReturnTrue(String input) {
        assertThat(CustomStringUtils.isBlank(input)).isTrue();
    }

    @DisplayName("공백 체크, 공백이 아닌 값 - False 반환")
    @Test
    void isBlank_ReturnFalse() {
        assertThat(CustomStringUtils.isBlank("bingbong")).isFalse();
    }
}
