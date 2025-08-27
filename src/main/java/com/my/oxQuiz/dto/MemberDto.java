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
    private String  id;
    private String password;
    private boolean status;

    // 엔티티를 받아서 Dto로 변환해 주는 함수
    public static MemberDto fromMemberEntity(MemberEntity member) {
        return new MemberDto(
                member.getNo(),
                member.getId(),
                member.getPassword(),
                member.isStatus()
        );
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static MemberEntity toDto(MemberDto dto) {
        MemberEntity member = new MemberEntity();
        member.setNo(dto.getNo());
        member.setId(dto.getId());
        member.setPassword(dto.getPassword());
        member.setStatus(dto.isStatus());
        return member;
    }
}
