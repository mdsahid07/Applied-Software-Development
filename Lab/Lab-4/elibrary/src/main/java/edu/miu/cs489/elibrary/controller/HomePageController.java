package edu.miu.cs489.elibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping({"/", "/elibrary", "/elibrary/home", "/home"})
    public String displayHomePage() {
        // resolves to src/main/resources/templates/home/index.html
        return "home/index";
    }
}
