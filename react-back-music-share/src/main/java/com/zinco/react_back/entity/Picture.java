package com.zinco.react_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Picture_ID")
    private Integer pictureId;

    @Column(name = "Picture_File")
    private String pictureFile;

    @ManyToOne
    @JoinColumn(name = "User_ID", referencedColumnName = "ID")
    private User user;
}
