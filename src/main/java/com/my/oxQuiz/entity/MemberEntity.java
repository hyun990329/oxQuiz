package com.my.oxQuiz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "member")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class MemberEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "id", unique = true, nullable = false, length = 50)
    private String id;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private boolean admin;

    @Column(nullable = false)
    private int answerTrue;

    @Column(nullable = false)
    private int answerFalse;
}