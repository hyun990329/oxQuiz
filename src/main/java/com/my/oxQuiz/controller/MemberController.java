package com.my.oxQuiz.controller;

import com.my.oxQuiz.entity.MemberEntity;
import com.my.oxQuiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService service;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        System.out.println("Accessing /user/signup");
        return "user/signup"; // Thymeleaf 템플릿
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String id, @RequestParam String password, @RequestParam String passwordConfirm, Model model) {
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다");
            return "user/signup";
        }
        try {
            service.signup(id, password);
            return "redirect:/user/login?signup=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/signup";
        }
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String signup,
                            Model model) {
        System.out.println("Accessing /user/login");
        model.addAttribute("error", error);
        model.addAttribute("signup", signup);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, HttpSession session) {
        System.out.println("Attempting login for id: " + id);
        return service.login(id, password)
                .map(m -> {
                    session.setAttribute("member", m);
                    session.setAttribute("admin", m.isAdmin());
                    return "redirect:/main";
                })
                .orElse("redirect:/user/login?error=true");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {
        MemberEntity m = (MemberEntity) session.getAttribute("member");
        if (m == null) return "redirect:/user/login";
        model.addAttribute("member", m);
        return "user/myPage";
    }

    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        MemberEntity member = (MemberEntity) session.getAttribute("member");
        model.addAttribute("member", member);
        return "main";
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        MemberEntity member = (MemberEntity) session.getAttribute("member");
        response.put("loggedIn", member != null);
        if (member != null) {
            response.put("id", member.getId());
            response.put("admin", member.isAdmin());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mypage/api")
    public ResponseEntity<MemberEntity> myPageApi(HttpSession session) {
        MemberEntity member = (MemberEntity) session.getAttribute("member");
        return member != null ? ResponseEntity.ok(member) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}