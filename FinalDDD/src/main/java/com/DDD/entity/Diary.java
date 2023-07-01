package com.DDD.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "diary")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Diary {
    @Id
    @Column(name = "diary_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String regDate;

    private double rateStar;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibit_no")
    private Exhibitions exhibition;

    @Builder
    public Diary(Member member, String regDate, double rateStar, String comment, Exhibitions exhibition) {
        this.member = member;
        this.regDate = regDate;
        this.rateStar = rateStar;
        this.comment = comment;
        this.exhibition = exhibition;
    }
}
