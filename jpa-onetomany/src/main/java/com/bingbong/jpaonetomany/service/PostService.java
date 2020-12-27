package com.bingbong.jpaonetomany.service;


import com.bingbong.jpaonetomany.domain.Comment;
import com.bingbong.jpaonetomany.domain.Post;
import com.bingbong.jpaonetomany.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    @Transactional
    public List<String> findAllPosts() {
        return extractCommentsContents(postRepository.findAll());
    }

    @Transactional
    public Post findPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(IllegalAccessError::new);
    }

    @Transactional
    public void setCommentNull(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(IllegalAccessError::new);
        List<Comment> comments = post.getComments();
        Comment comment = comments.get(0);
        comment.setPost(null);
    }

    @Transactional
    public List<String> findCommentContentsByPostId() {
        return postRepository.findFirstByOrderById()
            .orElseThrow(IllegalAccessError::new)
            .getComments()
            .stream()
            .map(Comment::getContent)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<String> findAllCommentContentsByOrigin() {
        return extractCommentsContents(postRepository.findAll());
    }

    @Transactional
    public List<String> findAllCommentContentsByJoinFetch() {
        return extractCommentsContents(postRepository.findAllJoinFetch());
    }

    @Transactional
    public List<String> findAllCommentContentsByEntityGraph() {
        return extractCommentsContents(postRepository.findAllEntityGraph());
    }

    private List<String> extractCommentsContents(List<Post> posts) {
        log.info(">>>>>>>>[모든 댓글을 추출한다]<<<<<<<<<");
        log.info("Post Size : {}", posts.size());

        return posts.stream()
            .map(a -> a.getComments().get(0).getContent())
            .collect(Collectors.toList());
    }
}
