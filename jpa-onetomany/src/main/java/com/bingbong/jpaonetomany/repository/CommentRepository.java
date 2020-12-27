package com.bingbong.jpaonetomany.repository;

import com.bingbong.jpaonetomany.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
