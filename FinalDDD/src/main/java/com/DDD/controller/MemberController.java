package com.DDD.controller;


import com.DDD.dto.MemberRequestDto;
import com.DDD.dto.MemberResponseDto;
import com.DDD.dto.TokenDto;
import com.DDD.entity.Member;
import com.DDD.repository.MemberRepository;
import com.DDD.service.AuthService;
import com.DDD.service.MemberService;
import com.DDD.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;


    // AccessToken 재발급 코드
    @PostMapping("/auth/token")
    public ResponseEntity<TokenDto> renewAccessToken(@RequestBody TokenDto requestDto){
        TokenDto renewDto = tokenService.createNewAccessToken(requestDto.getRefreshToken());
        return ResponseEntity.ok(renewDto);
    }

    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(@RequestParam("token") String token) {
        Optional<Member> optionalUser = memberRepository.findByEmailCheckToken(token);
        if (optionalUser.isPresent()) {
            Member member = optionalUser.get();
            member.setActive(true);
            member.setEmailCheckToken(null); // after validation you can null the token
            memberRepository.save(member);
            return "Email is successfully verified";
        } else {
            return "Invalid token";
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<Boolean> forgotPw(@RequestBody Map<String, String> forgotEmail) {
        String email = forgotEmail.get("email");
        boolean sendNewPassword = memberService.forgotEmail(email);
        return ResponseEntity.ok(sendNewPassword);
    }



    @PostMapping("/emaildup")
    public ResponseEntity<Boolean> emailDup(@RequestBody Map<String, String> emailDupData) {
        String email = emailDupData.get("email");
        boolean isDuplicate = memberService.emailDupCk(email);
        return ResponseEntity.ok(isDuplicate);
    }

    @PostMapping("/nicknamedup")
    public ResponseEntity<Boolean> nicknameDup(@RequestBody Map<String, String> nicknameDupData) {
        String nickname = nicknameDupData.get("nickname");
        boolean isDuplicate = memberService.nicknameDupCk(nickname);
        return ResponseEntity.ok(isDuplicate);
    }



}
