package com.bingbong.jpaonetomany.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.bingbong.jpaonetomany.domain.Comment;
import com.bingbong.jpaonetomany.domain.Post;
import com.bingbong.jpaonetomany.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(CustomPostService.class)
class PostServiceTestWithDataJpaTest {

    @Autowired
    private CustomPostService postService;

    @Autowired
    private PostRepository postRepository;

    private Post savedPost;

    @BeforeEach
    void setup() {
        Post post = Post.builder()
            .content("글 입니다")
            .build();
        Comment comment = Comment.builder()
            .content("댓글입니다")
            .build();
        post.addComment(comment);

        savedPost = postRepository.save(post);
    }

    @DisplayName("@Transactional을 붙이지 않았음에도 테스트가 통과된다!")
    @Test
    void accessTest() {
        assertThatCode(() -> postService.accessComment(savedPost.getId()))
            .doesNotThrowAnyException();
    }
}
