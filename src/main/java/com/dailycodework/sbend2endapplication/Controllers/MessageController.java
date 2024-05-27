package com.dailycodework.sbend2endapplication.Controllers;


import com.dailycodework.sbend2endapplication.entities.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
        @GetMapping("/message")
        public String message(Model model) {
            Message message = new Message("data.message");
            model.addAttribute("message", message);
            return "message";
        }
}