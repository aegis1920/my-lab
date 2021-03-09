package com.bingbong.jpaonetomany.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.bingbong.jpaonetomany.domain.Post;
import com.bingbong.jpaonetomany.repository.PostRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PostServiceTransactionalTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PostRepository postRepository;

    @DisplayName("트랜잭션없이 persist할 때 TransactionRequiredException 에러")
    @Test
    void persistTest_ThrownException() {
        Post post = Post.builder()
            .content("글 입니다")
            .build();

        assertThatThrownBy(() -> em.persist(post))
            .isInstanceOf(TransactionRequiredException.class);
    }

    @DisplayName("트래잭션이 있다면 persist 통과")
    @Test
    @Transactional
    void persistTest() {
        Post post = Post.builder()
            .content("글 입니다")
            .build();

        em.persist(post);
        Post foundPost = em.find(Post.class, post.getId());

        assertThat(foundPost.getContent()).isEqualTo("글 입니다");
    }

    @DisplayName("repository.save는 내부에 트래잭션이 있기 때문에 통과, em.find는 단순히 em에 id값 조회(트랜잭션 없이 가능)")
    @Test
    void saveTest() {
        Post post = Post.builder()
            .content("글 입니다")
            .build();

        postRepository.save(post);

        Post foundPost = em.find(Post.class, post.getId());

        assertThat(foundPost.getContent()).isEqualTo("글 입니다");
    }
}
