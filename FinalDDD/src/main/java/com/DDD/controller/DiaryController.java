package com.DDD.controller;

import com.DDD.dto.DiaryDto;
import com.DDD.dto.MemberDto;
import com.DDD.entity.Diary;
import com.DDD.service.DiaryService;
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

    @PostMapping("/{exhibitNo}")
    public ResponseEntity<Diary> updateDiary(@PathVariable Long memberId,
                                               @PathVariable Long exhibitNo,
                                               @RequestBody Map<String, String> updateData){
//            if (updateData == null || updateData.get("rateStar") == null || updateData.get("comment") == null) {
//                return ResponseEntity.badRequest().build();
//            }
        double rateStar = Double.parseDouble(updateData.get("rateStar"));
        String comment = updateData.get("comment");
        Diary updatedDiary = diaryService.updateDiary(memberId, exhibitNo, rateStar, comment);
        if (updatedDiary != null) {
            return ResponseEntity.ok(updatedDiary);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
//    public ResponseEntity<Boolean> emailDup(@RequestBody Map<String, String> emailDupData) {
//        String email = emailDupData.get("email");
//        boolean isDuplicate = memberService.emailDupCk(email);
//        return ResponseEntity.ok(isDuplicate);
//    }





}
