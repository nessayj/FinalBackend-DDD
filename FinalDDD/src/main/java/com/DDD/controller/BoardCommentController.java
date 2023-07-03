package com.DDD.controller;

import com.DDD.dto.BoardCommentDto;
import com.DDD.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:3000")
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    // 댓글 작성
    @PostMapping("/commentWrite")
    public ResponseEntity<Boolean> commentWrite(@RequestBody BoardCommentDto boardCommentDto) {
        boolean result = boardCommentService.createComments(boardCommentDto);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}



