package com.bingbong.cache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/etag")
    public String homeWithEtag() {
        return "/index";
    }

    @GetMapping("/no-etag")
    public String homeWithNoEtag() {
        return "/index";
    }
}
