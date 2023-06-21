package com.DDD.repository;

import com.DDD.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일 전체 조회
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);
//    boolean existsByEmail(String email);
//    List<Member> findAll();

}