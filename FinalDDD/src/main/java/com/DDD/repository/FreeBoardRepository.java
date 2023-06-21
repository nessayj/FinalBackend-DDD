package com.DDD.repository;

// DB 에 접근 가능한 Repository 생성(DAO), 요청과 응답만 처리
// JpaRepository<테이블명, PK에 대한 데이터형>

import com.DDD.entity.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 자유게시판 카테고리별 목록 조회
@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    List<FreeBoard> findByCategory(String category);
}

