package com.my.oxQuiz.controller;

import com.my.oxQuiz.entity.MemberEntity;
import com.my.oxQuiz.entity.QuizEntity;
import com.my.oxQuiz.service.MemberService;
import com.my.oxQuiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final MemberService memberService;

    @GetMapping("/play")
    public String play(Model model, HttpSession session) {
        MemberEntity member = (MemberEntity) session.getAttribute("member");
        if (member == null || !member.isStatus()) return "redirect:/user/login";
        quizService.selectOneRandomQuiz().ifPresent(quiz -> model.addAttribute("quiz", quiz));
        return "quiz/play";
    }

    @PostMapping("/check")
    public String check(@RequestParam Integer id, @RequestParam Boolean answer, HttpSession session) {
        MemberEntity member = (MemberEntity) session.getAttribute("member");
        if (member == null || !member.isStatus()) return "redirect:/quiz/play";
        boolean correct = quizService.checkQuiz(id, answer);
        memberService.updateResult(member.getNo(), correct);
        return "redirect:/quiz/play";
    }

    @PostMapping("/insert")
    public String insert(@RequestParam String question, @RequestParam Boolean answer, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/main";
        QuizEntity quiz = new QuizEntity();
        quiz.setQuestion(question);
        quiz.setAnswer(answer);
        quizService.insertQuiz(quiz);
        return "redirect:/admin/pending"; // CRUD UI 추가 시 수정
    }

    private boolean isAdmin(HttpSession session) {
        Object admin = session.getAttribute("admin");
        return admin instanceof Boolean && (Boolean) admin;
    }
}