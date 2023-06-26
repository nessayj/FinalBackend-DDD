package com.DDD.controller;
import com.DDD.dto.FreeBoardDto;
import com.DDD.entity.FreeBoard;
import com.DDD.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boardList") // CRUD 작업이 필요할 시 GetMapping 보다는 requestMapping 으로
public class FreeBoardController {
    @Autowired
    private final FreeBoardService freeBoardService;

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Boolean> boardWrite(@RequestBody Map<String, String> data){
        String author = data.get("author");
        String category = data.get("category");
        String region = data.get("region");
        String title =  data.get("title");
        String image =  data.get("image");
        String contents = data.get("contents");

        boolean result = freeBoardService.createBoards(author, category, region, title, image, contents);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 상세 조회
    @GetMapping("/boardView/{boardNo}")
    public ResponseEntity<FreeBoard> getBoard(@PathVariable("boardNo") Long boardNo) {
        return new ResponseEntity(freeBoardService.selectBoardOne(boardNo), HttpStatus.OK);
    }

    // 게시글 수정(최종)
    @PutMapping("/{boardNo}")
    public ResponseEntity<Boolean> editBoards(@PathVariable Long boardNo, @RequestBody FreeBoardDto freeBoardDto) {
        boolean result = freeBoardService.updateBoards(boardNo, freeBoardDto);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }




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
