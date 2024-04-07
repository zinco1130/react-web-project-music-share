package com.zinco.react_back.repository;

import com.zinco.react_back.entity.Comment;
import com.zinco.react_back.entity.Music;
import com.zinco.react_back.entity.Tab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {
    List<Music> findByTitleContainingIgnoreCase(String title);

    boolean existsByTitleAndSinger(String title, String singer);
}