package com.bingbong.kotlinpractice.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class PostResponseTest {

    @DisplayName("data class Test")
    @Test
    internal fun responseTest() {

        val postResponse = PostResponse(1, "title", "writing")

        assertThat(postResponse.postId).isEqualTo(1)
        assertThat(postResponse.title).isEqualTo("title")
        assertThat(postResponse.writing).isEqualTo("writing")
    }

    @DisplayName("companion object Test")
    @Test
    internal fun companionObjectTest() {

        val postResponse = PostResponse.Companion

        assertThat(postResponse.sta).isNotNull
        assertThat(PostResponse.sta).isNotNull
        assertThat(postResponse.calc(1)).isEqualTo(11)
        assertThat(PostResponse.calc(1)).isEqualTo(11)
    }
}
