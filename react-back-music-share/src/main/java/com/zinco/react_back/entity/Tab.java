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
@Table(name = "Tab")
public class Tab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tab_ID")
    private Integer tabId;

    @Column(name = "Tab_Page")
    private String tabPage;

    @Column(name = "Tab_Detail")
    private Integer tabDetail;
}