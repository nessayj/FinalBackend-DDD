package com.DDD.entity;

import com.DDD.constant.Authority;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
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


//    @Builder
//    public Member( String email, String name, String password, Authority authority, String tel, String nickName, String instagram, String introduce, String profileImg, String backgroundImg) {
//        this.email = email;
//        this.name = name;
//        this.password = password;
//        this.authority = authority;
//        this.tel = tel;
//        this.nickName = nickName;
//        this.instagram = instagram;
//        this.introduce = introduce;
//        this.profileImg = profileImg;
//        this.backgroundImg = backgroundImg;
//    }

}
