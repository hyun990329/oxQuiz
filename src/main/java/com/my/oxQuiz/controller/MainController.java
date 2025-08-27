package com.my.oxQuiz.controller;

import com.my.oxQuiz.entity.MemberEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/main"})
    public String main(Model model, HttpSession session) {
        MemberEntity member = (MemberEntity) session.getAttribute("member");
        model.addAttribute("member", member);
        return "main";
    }
}
