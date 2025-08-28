package com.my.oxQuiz.dto;

import com.my.oxQuiz.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long no;
    private String id;
    private String password;
    private boolean status;
    private boolean admin;
    private int answerTrue;
    private int answerFalse;

    public static MemberDto fromMemberEntity(MemberEntity member) {
        return new MemberDto(
                member.getNo(),
                member.getId(),
                member.getPassword(),
                member.isStatus(),
                member.isAdmin(),
                member.getAnswerTrue(),
                member.getAnswerFalse()
        );
    }

    public static MemberEntity toMemberEntity(MemberDto dto) {
        MemberEntity member = new MemberEntity();
        member.setNo(dto.getNo());
        member.setId(dto.getId());
        member.setPassword(dto.getPassword());
        member.setStatus(dto.isStatus());
        member.setAdmin(dto.isAdmin());
        member.setAnswerTrue(dto.getAnswerTrue());
        member.setAnswerFalse(dto.getAnswerFalse());
        return member;
    }
}