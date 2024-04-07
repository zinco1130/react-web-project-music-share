package com.zinco.react_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Comment_ID")
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tab_ID", referencedColumnName = "Tab_ID")
    private Tab tab;

    @Column(name = "Comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Picture_ID", referencedColumnName = "Picture_ID")
    private Picture picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Music_ID", referencedColumnName = "Music_ID")
    private Music music;
}