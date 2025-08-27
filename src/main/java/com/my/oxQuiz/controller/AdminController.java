package com.my.oxQuiz.controller;

import com.my.oxQuiz.entity.MemberEntity;
import com.my.oxQuiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService service;

    private boolean isAdmin(HttpSession session) {
        Object admin = session.getAttribute("admin");
        return admin instanceof Boolean && (Boolean) admin;
    }

    // 승인 대기 회원 목록
    @GetMapping("/pending")
    public String pending(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/user/login";
        List<MemberEntity> waiting = service.pendingMembers();
        model.addAttribute("waiting", waiting);
        return "admin/pending";
    }

    // 회원 승인
    @PostMapping("/approve")
    public String approve(@RequestParam Long no, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/user/login";
        service.approveMember(no);
        return "redirect:/admin/pending";
    }
}