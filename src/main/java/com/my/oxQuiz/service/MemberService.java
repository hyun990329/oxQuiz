package com.my.oxQuiz.service;

import com.my.oxQuiz.entity.MemberEntity;
import com.my.oxQuiz.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public MemberEntity signup(String id, String password) {
        if ("root".equals(id) && "admin".equals(password)) {
            if (repository.existsAdminTrue()) {
                throw new IllegalStateException("관리자가 이미 존재합니다");
            }
            // 관리자 정보 입력 및 권한 부여
            MemberEntity admin = MemberEntity.builder()
                    .Id(id)
                    .password(password)
                    .status(true)
                    .admin(true)
                    .answerTrue(0)
                    .answerFalse(0)
                    .build();
            return repository.save(admin);
        }
        // 유저 가입 정보 입력 (승인 대기)
        MemberEntity user = MemberEntity.builder()
                .Id(id)
                .password(password)
                .status(false)
                .admin(false)
                .answerTrue(0)
                .answerFalse(0)
                .build();
        return repository.save(user);
    }

    // 로그인
    @Transactional(readOnly = true)
    public Optional<MemberEntity> login(String id, String password) {
        return repository.findById(Long.valueOf(id))
                .filter(x -> x.getPassword().equals(password))
                .filter(MemberEntity::isStatus);
    }

    // 승인 대기중인 유저 목록
    @Transactional(readOnly = true)
    public List<MemberEntity> pendingMembers() {
        return repository.findAllByAdminFalseAndStatusFalse();
    }

    // 관리자 -> 유저 승인
    public void approveMember(Long no) {
        MemberEntity m = repository.findById(no).orElseThrow();
        m.setStatus(true);
        repository.save(m);
    }

    // 문제 풀이 결과 반영
    public void updateResult(Long no, boolean correct) {
        MemberEntity m = repository.findById(no).orElseThrow();
        if(correct) m.setAnswerTrue(m.getAnswerTrue() + 1);
        else m.setAnswerFalse(m.getAnswerFalse() + 1);
        repository.save(m);
    }
}
