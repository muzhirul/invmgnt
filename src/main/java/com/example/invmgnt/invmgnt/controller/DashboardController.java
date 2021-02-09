package com.example.invmgnt.invmgnt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {
    @RequestMapping({ "/","/home"})
    public String homePage(){
        return "home";
    }

}
