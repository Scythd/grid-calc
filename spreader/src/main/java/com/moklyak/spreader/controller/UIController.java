package com.moklyak.spreader.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@Controller
public class UIController {



    @GetMapping("/")
    public ModelAndView index(Model model){
        return new ModelAndView("index", model.asMap());
    }

    @PostMapping("/ui")
    public ModelAndView indexHandler(Model model){

        return new ModelAndView("index", model.asMap());
    }




}
