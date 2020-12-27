package com.bingbong.jpaonetomany.service;


import com.bingbong.jpaonetomany.domain.Comment;
import com.bingbong.jpaonetomany.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(IllegalAccessError::new);
    }
}
