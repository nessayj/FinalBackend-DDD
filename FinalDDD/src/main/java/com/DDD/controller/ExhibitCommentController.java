package com.DDD.controller;


import com.DDD.service.ExhibitCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/exhibitComment")
@CrossOrigin("http://localhost:3000")
public class ExhibitCommentController {
    private ExhibitCommentService exhibitCommentService;

    // 한줄평작성
    @PostMapping
    public boolean writeComment(@RequestBody Map<String, Object> data) {
        try {
            Long id = Long.parseLong(data.get("id").toString());
            Long exhibitNo = Long.parseLong(data.get("exhibitNo").toString());
            Long starRates = Long.parseLong(data.get("starRates").toString());
            String comment = data.get("comment").toString();

            exhibitCommentService.writeComment(id, exhibitNo, starRates, comment);

            return true;
        } catch (Exception e) {
            System.err.println("오류 발생!! 🤬🤬 : " + e.getMessage());
            return false;
        }

    }
}
