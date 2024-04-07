package com.zinco.react_back.repository;

import com.zinco.react_back.entity.Comment;
import com.zinco.react_back.entity.Recommend;
import com.zinco.react_back.entity.Tab;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

    @EntityGraph(attributePaths = {"music", "picture"})
    @Query("SELECT r FROM Recommend r")
    List<Recommend> findAllWithDetails();

    List<Recommend> findByTab(Tab tab);
}
