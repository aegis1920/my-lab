package com.bingbong.kotlinpractice.variable

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

// internal 접근제한자. 같은 모듈. 아무것도 안 붙이면 public
internal class VariableTest {

    @DisplayName("변수 테스트")
    @Test
    internal fun variableTest() {
        var number = 15
        val numberSpecify = 15
        var str = "Hello World!"
        val strSpecify = "Hello World!"

        assertThat(number).isEqualTo(numberSpecify)
        assertThat(str).isEqualTo(strSpecify)
    }
}
