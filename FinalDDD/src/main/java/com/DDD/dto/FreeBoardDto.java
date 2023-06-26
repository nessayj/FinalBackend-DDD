package com.DDD.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FreeBoardDto {  // 프론트엔드와 주고 받을 때 사용
    private Long boardNo; // 게시판 번호
    private String author; // 작성자 아이디
    private String category; // 게시판 카테고리
    private String region; // 지역 카테고리
    private String title; // 게시판 제목
    private String contents; // 게시판 내용
    private String image; // 게시판 이미지
    private int views; // 게시판 조회수
    private LocalDateTime writeDate; // 게시판 작성일
    private String profileImg; // 프로필 이미지 추가

}
