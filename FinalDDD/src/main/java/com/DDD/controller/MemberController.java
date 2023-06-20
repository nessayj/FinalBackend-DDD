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
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signupMember(@RequestBody Map<String, Object> data){
        String email = (String) data.get("email");
        String password = (String) data.get("password");
        boolean result = memberService.signUpMember(email, password);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
