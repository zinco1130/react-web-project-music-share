package com.zinco.react_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class ReactBackController {
    @GetMapping("/api")
    public String index() {
        return "index";
    }
}
