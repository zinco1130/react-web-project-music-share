package com.zinco.react_back.service;

import com.zinco.react_back.DTO.CommentDto;
import com.zinco.react_back.DTO.MusicDto;
import com.zinco.react_back.DTO.RecommendationDto;
import com.zinco.react_back.entity.Comment;
import com.zinco.react_back.entity.Music;
import com.zinco.react_back.entity.Recommend;
import com.zinco.react_back.repository.CommentRepository;
import com.zinco.react_back.repository.MusicRepository;
import com.zinco.react_back.repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RecommendRepository recommendRepository;
    @Autowired
    private MusicRepository musicRepository;

    public void recommendMusic(Recommend recommend) {
        entityManager.getTransaction().begin();
        entityManager.persist(recommend);
        entityManager.getTransaction().commit();
    }
    public List<Recommend> getMusicRecommendations() {
        return recommendRepository.findAllWithDetails();
    }

    public RecommendationDto toRecommendationDto(Recommend recommend) {
        RecommendationDto dto = new RecommendationDto();
        dto.setId(recommend.getRecommendId().longValue());
        dto.setPicture(recommend.getPicture()); // Make sure Picture is eagerly fetched or convert to DTO
        dto.setContent(recommend.getContent());
        List<CommentDto> commentDtoList = commentRepository.findByTab(recommend.getTab()).stream()
                .map(this::convertToCommentDto) // Convert Music entities to MusicDto
                .distinct() // Ensure no duplicates if you expect any
                .collect(Collectors.toList());
        List<MusicDto> musicDtoList = commentRepository.findByTab(recommend.getTab()).stream()
                .map(comment -> convertToMusicDto(comment.getMusic())) // Convert Music entities to MusicDto
                .distinct() // Ensure no duplicates if you expect any
                .toList();
        dto.setMusic(recommend.getMusic());
        dto.setMusicList(musicDtoList);
        dto.setCommentList(commentDtoList);

        return dto;
    }

    private CommentDto convertToCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setComment(comment.getComment());
        return dto;
    }

    private MusicDto convertToMusicDto(Music music) {
        MusicDto dto = new MusicDto();
        if (music == null) {
            return dto;
        }
        dto.setTitle(music.getTitle());
        dto.setSinger(music.getSinger());
        return dto;
    }

    public List<Recommend> getAllRecommendations() {
        return entityManager.createQuery("FROM Recommend", Recommend.class).getResultList();
    }
}
