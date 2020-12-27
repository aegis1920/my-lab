package com.bingbong.jpaonetomany.repository;


import com.bingbong.jpaonetomany.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select a from Post a join fetch a.comments")
    List<Post> findAllJoinFetch();

    @Query("select distinct a from Post a join fetch a.comments")
    List<Post> findAllJoinFetchWithDistinct();

    @EntityGraph(attributePaths = {"comments"})
    @Query("select a from Post a")
    List<Post> findAllEntityGraph();

    Optional<Post> findFirstByOrderById();
}
