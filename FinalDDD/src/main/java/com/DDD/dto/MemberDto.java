package com.DDD.dto;

import com.DDD.constant.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String email;
    private String name;
    private String password;
    private String tel;
    private String nickname;
    private String instagram;
    private String profileImg;
    private String backgroundImg;
    private String introduce;
    private boolean isActive;
    private Authority authority;

}
