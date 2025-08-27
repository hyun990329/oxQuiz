package com.my.oxQuiz.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;


@Entity
@Table(name = "member")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class MemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; // PK

    @Column(name = "id", unique = true, nullable = false, length = 50)
    private String Id;

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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}