package com.zinco.react_back.service;

import com.zinco.react_back.DTO.MusicDto;
import com.zinco.react_back.DTO.RecommendationDto;
import com.zinco.react_back.entity.Music;
import com.zinco.react_back.entity.Recommend;
import com.zinco.react_back.repository.MusicRepository;
import com.zinco.react_back.repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private final ManiaDbService maniaDbService;

    private final MusicRepository musicRepository;
    private final RecommendRepository recommendRepository;

    @Autowired
    public MusicService(ManiaDbService maniaDbService, MusicRepository musicRepository, RecommendRepository recommendRepository) {
        this.maniaDbService = maniaDbService;
        this.musicRepository = musicRepository;
        this.recommendRepository = recommendRepository;
    }


    public List<Music> searchMusicByTitle(String title) {
        List<Music> musicList = maniaDbService.getMusicInfo(title);
        return musicList;
    }

    public MusicDto toMusicDto(Music music) {
        MusicDto dto = new MusicDto();
        dto.setId(music.getMusicId().longValue());  // convert Integer to Long
        dto.setTitle(music.getTitle());
        return dto;
    }

    public void addMusic(Music music) {
        if (musicRepository.existsByTitleAndSinger(music.getTitle(), music.getSinger())) {
            return;
        }
        musicRepository.save(music);
    }
}
