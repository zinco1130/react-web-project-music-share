package com.zinco.react_back.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneralResponse {
    private boolean success;
    private String message;
}
