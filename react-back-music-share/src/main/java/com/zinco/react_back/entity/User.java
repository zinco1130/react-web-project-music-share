package com.zinco.react_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "User")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")
    private Integer id;

    @Column(name = "ID", nullable = false, unique = true)
    private String userId;

    @Column(name = "PW", nullable = false)
    private String password;

    @Column(name = "Nickname")
    private String nickname;
}
