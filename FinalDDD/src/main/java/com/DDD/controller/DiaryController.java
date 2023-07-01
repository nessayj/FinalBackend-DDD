package com.DDD.controller;

import com.DDD.dto.DiaryDto;
import com.DDD.dto.MemberDto;
import com.DDD.service.DiaryService;
import com.DDD.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("mypage/{memberId}/diary")
@CrossOrigin(origins = "http://localhost:3000")
public class DiaryController {
    private final MemberService memberService;
    private final DiaryService diaryService;

    @GetMapping
    public ResponseEntity<List<DiaryDto>> getDiaryByMemberId(@PathVariable Long memberId) {
        List<DiaryDto> diaryItems = diaryService.getDiaryByMemberId(memberId);
        return new ResponseEntity<>(diaryItems, HttpStatus.OK);
    }


}
