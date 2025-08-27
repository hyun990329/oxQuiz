package com.my.oxQuiz.controller;

import com.my.oxQuiz.entity.MemberEntity;
import com.my.oxQuiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class MemberController {
    private final MemberService service;

    // 회원가입 폼
    @GetMapping("/user/signup")
    public String signupForm() {
        return "user/signup";
    }

    // 회원가입 처리
    @PostMapping("/member/signup")
    public String signup(@RequestParam String id,
                         @RequestParam String password,
                         @RequestParam String passwordConfirm,
                         Model model) {
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

    // 로그인 폼
    @GetMapping("/user/login")
    public String loginForm(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String signup,
                            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("signup", signup);
        return "user/login";
    }

    // 로그인 처리 (status == true 만 성공하도록)
    @PostMapping("/member/login")
    public String login(@RequestParam String id,
                        @RequestParam String password,
                        HttpSession session) {
        return service.login(id, password)
                .map(m -> {
                    session.setAttribute("member", m);
                    session.setAttribute("admin", m.isAdmin());
                    return "redirect:/main";
                })
                .orElse("redirect:/user/login?error=true");
    }

    // 로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    // 마이페이지 (정답/오답/상태 확인)
    @GetMapping("/user/mypage")
    public String myPage(HttpSession session, Model model) {
        MemberEntity m = (MemberEntity) session.getAttribute("member");
        if (m == null) return "redirect:/user/login";
        model.addAttribute("member", m);
        return "user/myPage";
    }
}

