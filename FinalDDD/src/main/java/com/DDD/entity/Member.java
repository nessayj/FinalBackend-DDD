package com.DDD.entity;

import com.DDD.constant.Authority;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Setter @ToString
@NoArgsConstructor
public class Member {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    @Column(nullable = false)
    private String password;

    private String tel;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String instagram;

    private String profileImg;

    private String backgroundImg;

    private String introduce;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(nullable = false)
    private boolean isActive = true;

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<FreeBoard> freeBoardList = new ArrayList<>();


    @Builder // * .메소드 형태로 부르는 자바문법과 달리
    public Member( String email, String name, String password, Authority authority, String tel, String nickname, String instagram, String introduce, String profileImg, String backgroundImg, boolean isActive) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.authority = authority;
        this.tel = tel;
        this.nickname = nickname;
        this.instagram = instagram;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.backgroundImg = backgroundImg;
        this.isActive = isActive;
    }

}

