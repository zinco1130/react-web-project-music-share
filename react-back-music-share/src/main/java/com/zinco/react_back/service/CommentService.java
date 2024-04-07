package com.zinco.react_back.service;

import com.zinco.react_back.DTO.CommentDto;
import com.zinco.react_back.DTO.MusicDto;
import com.zinco.react_back.DTO.RecommendationDto;
import com.zinco.react_back.entity.Comment;
import com.zinco.react_back.entity.Recommend;
import com.zinco.react_back.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    public List<Comment> getCommentsByTabId(int tabId) {
        return entityManager.createQuery("FROM Comment WHERE tab.tabId = :tabId", Comment.class)
                .setParameter("tabId", tabId)
                .getResultList();
    }

    public List<Comment> getComments() {
        return commentRepository.findAllWithDetails();
    }

    public CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setComment(comment.getComment());

        return dto;
    }
}
