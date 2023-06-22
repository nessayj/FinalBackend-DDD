package com.DDD.controller;

import com.DDD.dto.MemberDto;
import com.DDD.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j

@RestController
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {
    private final MemberService memberService;

    @GetMapping("/getInfo")
    public ResponseEntity<List<MemberDto>> getInfo(@RequestBody Map<String, String> infoData) {
        String email = infoData.get("email");
        List<MemberDto> list = memberService.getMemberInfo(email);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/changeNickname")
    public ResponseEntity<Boolean> changeNickname(@RequestBody Map<String, String> infoData) {
        String email = infoData.get("email");
        String nickname = infoData.get("nickname");
        return ResponseEntity.ok(memberService.newNickname(email, nickname));
    }

    //////////////////
    @PostMapping("/changeName")
    public ResponseEntity<Boolean> changeName(@RequestBody Map<String, String> infoData) {
        String email = infoData.get("email");
        String name = infoData.get("name");
        return ResponseEntity.ok(memberService.newName(email, name));
    }

    @PostMapping("/changeTel")
    public ResponseEntity<Boolean> changeTel(@RequestBody Map<String, String> infoData) {
        String email = infoData.get("email");
        String tel = infoData.get("tel");
        return ResponseEntity.ok(memberService.newTel(email, tel));
    }

    @PostMapping("/changeInstagram")
    public ResponseEntity<Boolean> changeInstagram(@RequestBody Map<String, String> infoData) {
        String email = infoData.get("email");
        String instagram = infoData.get("instagram");
        return ResponseEntity.ok(memberService.newInstagram(email, instagram));
    }

    @PostMapping("/changeIntroduce")
    public ResponseEntity<Boolean> changeIntroduce(@RequestBody Map<String, String> infoData) {
        String email = infoData.get("email");
        String introduce = infoData.get("introduce");
        return ResponseEntity.ok(memberService.newIntroduce(email, introduce));
    }
}
