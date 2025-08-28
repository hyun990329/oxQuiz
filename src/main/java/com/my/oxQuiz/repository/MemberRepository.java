package com.my.oxQuiz.repository;

import com.my.oxQuiz.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByAdminIsTrue();
    List<MemberEntity> findAllByAdminFalseAndStatusFalse();
    @Query("SELECT m FROM MemberEntity m WHERE m.id = :id")
    Optional<MemberEntity> findByMemberId(@Param("id") String id);
}