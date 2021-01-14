package com.bingbong.effectivejava.item2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void builderTest() {
        Member member = Member.builder("bingbong", 20)
            .hobby("hobby")
            .mbti("intj")
            .build();

        assertThat(member).isNotNull();
    }
}
