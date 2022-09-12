package com.amigos.awsuploadimage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/card")
public class CardController {

    @GetMapping("")
    public String helloWorld(){
        return "pages/card/card";
    }
}