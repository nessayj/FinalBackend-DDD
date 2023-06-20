package com.DDD.entity;

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
    private String email;
    private String name;
    private String password;
    private String tel;
    private String nickName;
    private String instagram;
    private String profileImg;
    private String backgroundImg;

//    @Enumerated(EnumType.STRING)
//    private Authority authority;

    @Builder
    public Member( String email, String name, String password, String tel, String nickName, String instagram, String profileImg, String backgroundImg) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.tel = tel;
        this.nickName = nickName;
        this.instagram = instagram;
        this.profileImg = profileImg;
        this.backgroundImg = backgroundImg;
    }

}
