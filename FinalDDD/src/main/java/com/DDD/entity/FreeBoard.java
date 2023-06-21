package com.DDD.entity;

import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity // JPA 에 Entity 클래스 임을 알려줌.
@Table(name = "free_board") // 생성될 DB Table 이름을 정해줌(<->자바의 표기법은 대/소문자 구분하며 카멜 표기법을 따름), 생략도 가능
@Getter
@Setter
@ToString // 자동으로 문자열로 만들 수 있도록 오버라이딩(ToString)
public class FreeBoard {
    @Id // 해당 키가 Primary Key 임을 지정
    @Column(name = "board_no") // 인스턴스 필드명 boardNo -> board_no로 만들어달라는 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo; // Primary Key : 게시판 번호

    @ManyToOne(fetch = FetchType.LAZY) // N:1 다대일 관계
    @JoinColumn(name = "nickName",  referencedColumnName = "nickname", nullable = false)
    // user_id 는 게시판 테이블의 작성자, 'referencedColumnName' 는 참조 테이블로 회원정보의 닉네임을 참조한다는 내용
    private Member author; // 클래스에 대한 참조변수

    @Column(name = "board_ctg", nullable = false, length = 30) // 게시판 카테고리
    private String category;

    @Column(name = "board_region", nullable = false, length = 30) // 지역 카테고리
    private String region;

    @Column(name = "board_title", nullable = false, length = 300) // 글제목
    private String title;

    @Column(name = "board_contents", nullable = false, length = 4000) // 글본문
    private String contents;

    @Column(name = "board_img", nullable = false, length = 1000) // 게시판 이미지
    private String image;

    @ColumnDefault("0") // 조회수 (기본값 0 설정)
    @Column(nullable = false)
    private int views;

    @Timestamp // 작성일
    @Column(name = "write_date", nullable = false)
    private LocalDateTime writeDate = LocalDateTime.now();


    // 댓글 테이블 조인
    @OneToMany(mappedBy = "freeBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 하나의 게시물에 다수의 댓글 존재하므로
    private List<BoardComment> comments;

}
