package com.bingbong.jpaonetomany.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.bingbong.jpaonetomany.domain.Comment;
import com.bingbong.jpaonetomany.domain.Post;
import com.bingbong.jpaonetomany.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void setup() {
        List<Post> posts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Post post = Post.builder()
                .content("글 " + i)
                .build();

            post.addComment(Comment.builder().content("글의 첫 번째 댓글 " + i).build());
            post.addComment(Comment.builder().content("글의 두 번째 댓글 " + i).build());
            post.addComment(Comment.builder().content("글의 세 번째 댓글 " + i).build());
            posts.add(post);
        }

        postRepository.saveAll(posts);
    }

    @AfterEach
    void cleanAll() {
        postRepository.deleteAll();
    }

    @DisplayName("하나의 Post 조회 및 Comments 조회 시, N+1 쿼리가 발생하지 않음")
    @Test
    void findPostOccursN1() {
        List<String> commentsContents = postService.findCommentContentsByPostId();

        assertThat(commentsContents.size()).isEqualTo(3);
    }

    @DisplayName("여러 개의 Post를 조회 및 Comments 조회 시, N+1 쿼리가 발생")
    @Test
    void findAllPostsOccursN1() {
        List<String> commentsContents = postService.findAllCommentContentsByOrigin();

        assertThat(commentsContents.size()).isEqualTo(10);
    }

    @DisplayName("여러 개의 Post를 조회 및 Comments 조회 시, N+1 쿼리 해결 - Join Fetch")
    @Test
    void findAllPostsOccursN1_JoinFetch() {
        // 댓글의 개수가 3개라서 카티션 곱 문제가 발생한다.
        List<String> commentsContents = postService.findAllCommentContentsByJoinFetch();

        assertThat(commentsContents.size()).isEqualTo(30);
    }

    @DisplayName("여러 개의 Post를 조회 및 Comments 조회 시, N+1 쿼리 해결 - EntityGraph")
    @Test
    void findAllPostsOccursN1_EntityGraph() {
        // TODO: 2020/12/27 엔티티 그래프를 사용하면 카티션 곱 문제가 발생하지 않는다?
        List<String> commentsContents = postService.findAllCommentContentsByEntityGraph();

        assertThat(commentsContents.size()).isEqualTo(10);
        assertThat(commentsContents.size()).isNotEqualTo(30);
    }

    @DisplayName("하위 엔티티가 여러 개 있을 때 JoinFetch 사용 시 카티션 곱 문제 발생")
    @Test
    void findAllPosts_CartesianProduct() {
        // Post의 개수는 10개뿐인데 각 Comment의 수만큼 Post가 중복 발생
        List<Post> posts = postRepository.findAllJoinFetch();

        assertThat(posts.size()).isEqualTo(30);
    }

    @DisplayName("하위 엔티티가 여러 개 있을 때 JoinFetch, EntityGraph 사용 시 카티션 곱 문제 해결 - DISTINCT 추가")
    @Test
    void findAllPosts_CartesianProduct_Distinct() {
        List<Post> posts = postRepository.findAllJoinFetchWithDistinct();

        assertThat(posts.size()).isEqualTo(10);
    }
}
