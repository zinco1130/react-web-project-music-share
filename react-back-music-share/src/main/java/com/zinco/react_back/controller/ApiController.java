package com.zinco.react_back.controller;

import com.zinco.react_back.DTO.CommentDto;
import com.zinco.react_back.DTO.RecommendationDto;
import com.zinco.react_back.entity.Comment;
import com.zinco.react_back.entity.Music;
import com.zinco.react_back.entity.Recommend;
import com.zinco.react_back.entity.User;
import com.zinco.react_back.service.*;
import com.zinco.react_back.utils.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TabService tabService;

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping(value = {"/sayeon", "/musicReco"})
    public ResponseEntity<List<RecommendationDto>> getRecommendations() {
        List<Recommend> recommendations = recommendationService.getMusicRecommendations();
        List<RecommendationDto> dtoList = recommendations.stream()
                .map(recommend -> recommendationService.toRecommendationDto(recommend))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Music>> searchMusic(@RequestParam String title) {
        List<Music> musicList = musicService.searchMusicByTitle(title);
        return ResponseEntity.ok(musicList);
    }

    @PostMapping("/addMusic")
    public ResponseEntity<?> addMusic(@RequestBody Music music, HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getParameter("uri");
        String key = request.getParameter("key");
        System.out.println("uri: " + uri);
        System.out.println("key: " + key);
        musicService.addMusic(music);
        Comment comment = new Comment();
        comment.setMusic(music);
        comment.setTab(tabService.getTab(uri, key));
        commentService.addComment(comment);
        return ResponseEntity.ok("Success");
    }


    @PostMapping("/sayeonWrite")
    public ResponseEntity<?> addComment(@RequestBody Comment comment, HttpServletRequest request, HttpServletResponse response) {
        try {
            SecurityContext context = securityContextHolderStrategy.getContext();
            String userId = context.getAuthentication().getPrincipal().toString();
            String uri = request.getParameter("uri");
            String key = request.getParameter("key");
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(401).body(new GeneralResponse(false, "You must login first"));
            }
            comment.setUser(user);
            comment.setComment(comment.getComment());
            comment.setTab(tabService.getTab(uri, key));
            commentService.addComment(comment);
            return ResponseEntity.ok().body(new GeneralResponse(true, "Add comment successful"));
        } catch (Exception e) {
            logger.error("Add comment failed: ", e);
            return ResponseEntity.status(500).body(new GeneralResponse(false, "Add comment failed: " + e.getMessage()));
        }
    }
}
