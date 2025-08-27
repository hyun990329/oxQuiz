package com.my.oxQuiz.repository;

import com.my.oxQuiz.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository <MemberEntity, Long> {
    boolean existsAdminTrue();
    List<MemberEntity> findAllByAdminFalseAndStatusFalse();
}
