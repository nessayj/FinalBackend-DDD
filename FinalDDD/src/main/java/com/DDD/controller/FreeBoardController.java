package com.DDD.controller;
import com.DDD.dto.FreeBoardDto;
import com.DDD.entity.FreeBoard;
import com.DDD.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boardList") // CRUD 작업이 필요할 시 GetMapping 보다는 requestMapping 으로
public class FreeBoardController {
    @Autowired
    private final FreeBoardService freeBoardService;

    // 카테고리별 자유게시판 목록 조회
    @GetMapping("/{category}")
    public ResponseEntity<List<FreeBoardDto>> getFreeBoardsByCategory(@PathVariable("category") String category) {
        List<FreeBoardDto> freeBoardList = freeBoardService.getFreeBoardsByCategory(category);
        if (!freeBoardList.isEmpty()) {
            return new ResponseEntity<>(freeBoardList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
