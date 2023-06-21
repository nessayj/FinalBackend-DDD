package com.DDD.repository;

import com.DDD.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일 전체 조회 (이메일 중복 체크)
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email); // 이메일 존재여부 추가함

    // 닉네임 전체 조회 (닉네임 중복 체크)
    Optional<Member> findByNickname(String nickname);
//    boolean existsByEmail(String email);
//    List<Member> findAll();

}