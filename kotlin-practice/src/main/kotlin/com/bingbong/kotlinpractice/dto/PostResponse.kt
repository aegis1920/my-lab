package com.bingbong.kotlinpractice.dto


data class PostResponse(
        val postId: Long?,
        val title: String,
        val writing: String?,
) {

    companion object {
        // TODO : 나중에 of 메서드 작성해볼 것
        val sta = "I'm static"
        fun calc(i: Int) = i + 10
    }
}
