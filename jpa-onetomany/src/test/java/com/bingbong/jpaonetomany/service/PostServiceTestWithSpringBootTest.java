package com.bingbong.jpaonetomany.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.bingbong.jpaonetomany.domain.Post;
import com.bingbong.jpaonetomany.repository.PostRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTestWithSpringBootTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PostRepository postRepository;

    private Post savedPost;

    @BeforeEach
    void setup() {
        Post post = Post.builder()
            .content("글 입니다")
            .build();
        savedPost = postRepository.save(post);
    }

    @Test
    void findTest() {
        Post post = em.find(Post.class, savedPost.getId());

        assertThat(post.getId()).isEqualTo(savedPost.getId());
    }
}
