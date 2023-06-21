package com.DDD.controller;


import com.DDD.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signupMember(@RequestBody Map<String, Object> signupData)  {
        String email = (String) signupData.get("email");
        String password = (String) signupData.get("password");
        String nickname = (String) signupData.get("nickname");
        String name = (String) signupData.get("name");
        String tel = (String) signupData.get("tel");
        String instagram = (String) signupData.get("instagram");
        boolean result = memberService.signupMember(email, password, nickname, name, tel, instagram);
        if (result){
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @PostMapping("/emaildup")
    public ResponseEntity<Boolean> emailDup(@RequestBody Map<String, String> emailDupData) {
        String email = emailDupData.get("email");
        return ResponseEntity.ok(memberService.emailDupCk(email));
    }

    @PostMapping("/nicknamedup")
    public ResponseEntity<Boolean> nicknamelDup(@RequestBody Map<String, String> nicknameDupData) {
        String nickname = nicknameDupData.get("nickname");
        return ResponseEntity.ok(memberService.nicknameDupCk(nickname));
    }


}
