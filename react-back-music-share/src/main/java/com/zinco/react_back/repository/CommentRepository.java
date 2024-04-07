package com.zinco.react_back.repository;

import com.zinco.react_back.entity.Comment;
import com.zinco.react_back.entity.Music;
import com.zinco.react_back.entity.Recommend;
import com.zinco.react_back.entity.Tab;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByTab(Tab tab);

    @EntityGraph(attributePaths = {"user", "tab", "music", "picture"})
    @Query("SELECT c FROM Comment c")
    List<Comment> findAllWithDetails();
}
