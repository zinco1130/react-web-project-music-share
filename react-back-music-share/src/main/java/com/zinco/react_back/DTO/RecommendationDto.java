package com.zinco.react_back.DTO;

import com.zinco.react_back.entity.Music;
import com.zinco.react_back.entity.Picture;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendationDto {
    private Long id;
    private Music music;
    private List<MusicDto> musicList;
    private String content;
    private Picture picture;
    private List<CommentDto> commentList;
}
