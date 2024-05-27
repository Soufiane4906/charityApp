package com.dailycodework.sbend2endapplication.home;

import com.dailycodework.sbend2endapplication.services.CharityActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.AttributedString;

/**
 * @author Sampson Alfred
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private final CharityActionService charityActionService;

    public HomeController(CharityActionService charityActionService) {
        this.charityActionService = charityActionService;
    }

    @GetMapping
    public String homePage(Model mode){

        mode.addAttribute("charityActions", charityActionService.getAllCharityActions());

        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/error")
    public String error(){
        return "error";
    }

}
