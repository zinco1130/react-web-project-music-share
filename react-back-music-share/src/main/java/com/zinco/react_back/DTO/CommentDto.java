package com.zinco.react_back.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String comment;
    private String userName;
}

