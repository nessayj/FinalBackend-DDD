package com.DDD.controller;

import com.DDD.dto.MemberDto;
import com.DDD.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@CrossOrigin(origins = "http://localhost:3000")
public class MyPageController {
    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<List<MemberDto>> getInfo(@RequestParam("email") String email) {
        System.out.println("연결받음");
        List<MemberDto> list = memberService.getMemberInfo(email);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @PostMapping("/changeNickname")
    public ResponseEntity<Boolean> changeNickname(@RequestBody Map<String, String> infoData) {
        String email = infoData.get("email");
        String nickname = infoData.get("nickname");
        return ResponseEntity.ok(memberService.newNickname(email, nickname));
    }

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

//    @PostMapping("/memberdelete")
//    public ResponseEntity<Map<?, ?>> memberDelete( HttpServletResponse response, @RequestBody Map<String, Object> Data) {
//        String email = (String) Data.get("email");
//        String password = (String) Data.get("password");
//        return ResponseEntity.ok(memberService.memberDelete(email, password));
//
//    }
//    @PostMapping("/memberdelete")
//    public ResponseEntity<Map<?, ?>> memberDelete(
//            HttpServletResponse response,
//            @CookieValue(value = "token", required = false) String token,
//            @RequestBody Map<String, Object> Data) throws Exception {
//        String email = (String) Data.get("email");
//        String password = (String) Data.get("password");
//        Map<String ,String> map = new HashMap<>();
//        if(token != null){
//            log.info("로그인상태입니당");
//            String memberNumStr = jwtController.tokenCheck(token);
//            Long memberNum = Long.parseLong(memberNumStr);
//            map = memberService.memberDelete(email, password);
//            if(map.get("memberDelete").equals("OK")){
//                Cookie cookie = new Cookie("token", null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
//                cookie.setMaxAge(0); // 유효시간을 0으로 설정
//                cookie.setHttpOnly(true);
//                cookie.setPath("/");
//                response.addCookie(cookie);
//            }
//        }else {
//            map.put("memberDelete", "loginError");
//        }
//        return ResponseEntity.ok().body(map);
//    }



}
