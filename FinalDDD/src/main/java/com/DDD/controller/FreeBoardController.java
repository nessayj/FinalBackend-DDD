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

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boardList") // CRUD 작업이 필요할 시 GetMapping 보다는 requestMapping 으로
@CrossOrigin(origins = "http://localhost:3000")
public class FreeBoardController {
    @Autowired
    private final FreeBoardService freeBoardService;

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Boolean> boardWrite(@RequestBody FreeBoardDto freeBoardDto, Principal principal) {
        String category = freeBoardDto.getCategory();
        String region = freeBoardDto.getRegion();
        String title = freeBoardDto.getTitle();
        String image = freeBoardDto.getImage();
        String contents = freeBoardDto.getContents();

        Long id = freeBoardDto.getId(); // FreeBoardDto 에서 작성자 정보 가져오기

        boolean result = freeBoardService.createBoards(id, category, region, title, image, contents);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }




//    // 게시글 작성(2차 작업-작성자넣은상태)
//    @PostMapping("/write")
//    public ResponseEntity<Boolean> boardWrite(@RequestBody Map<String, String> data){
//        String author = data.get("author");
//        String category = data.get("category");
//        String region = data.get("region");
//        String title =  data.get("title");
//        String image =  data.get("image");
//        String contents = data.get("contents");
//
//        boolean result = freeBoardService.createBoards(author, category, region, title, image, contents);
//
//        if (result) {
//            return new ResponseEntity<>(true, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//        }
//    }




    // 게시글 상세 조회
    @GetMapping("/boardView/{boardNo}")
    public ResponseEntity<FreeBoard> getBoard(@PathVariable("boardNo") Long boardNo) {
        return new ResponseEntity(freeBoardService.selectBoardOne(boardNo), HttpStatus.OK);
    }

    // 게시글 수정(최종) + 작성자 정보 예외처리 추가
//    @PutMapping("/{boardNo}")
    @PutMapping("/boardView/{boardNo}")
    public ResponseEntity<Boolean> editBoards(@PathVariable Long boardNo, @RequestBody FreeBoardDto freeBoardDto, Principal principal) {

        // 사용자 정보 가져오기
//        String id = principal.getName(); // id(member Pk)의 이름을 문자열로 반환

        // 사용자 정보 가져오기(수정사항)
        if(principal == null){
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        String id = principal.getName(); // id(member Pk)의 이름을 문자열로 반환

        try {
            boolean result = freeBoardService.updateBoards(boardNo, freeBoardDto, Long.parseLong(id));

            if (result) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (EntityNotFoundException e) { // 게시물 존재 여부에 따른 예외처리
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) { // 작성자 일치 여부에 따른 예외처리
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/boardView/{boardNo}")
    public ResponseEntity<Boolean> delBoards(@PathVariable Long boardNo, Principal principal) {

//        // 사용자 정보 가져오기
//        String id = principal.getName();

        // 사용자 정보 가져오기(수정사항)
        if(principal == null){
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        String id = principal.getName(); // id(member Pk)의 이름을 문자열로 반환


        try {
            boolean result = freeBoardService.deleteBoards(boardNo, Long.parseLong(id));

            if (result) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (EntityNotFoundException e) {  // 게시물 존재여부에 따른 예외처리문
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) { // 작성자 일치여부에 따른 예외처리문
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }



    // 카테고리별 자유게시판 목록 조회
    @GetMapping("/{category}")
    public ResponseEntity<List<FreeBoardDto>> getFreeBoardsByCategory(@PathVariable("category") String category) {
        log.info("Received request to get free boards by category: {}", category);
        List<FreeBoardDto> freeBoardList = freeBoardService.getFreeBoardsByCategory(category);
        if (!freeBoardList.isEmpty()) {
            return new ResponseEntity<>(freeBoardList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 자유게시판 검색 키워드에 해당하는 리스트 불러오기
    @GetMapping("/searchList")
    public ResponseEntity<List<FreeBoardDto>> searchListLoad(@RequestParam String keyword) {
        List<FreeBoardDto> freeBoardList = freeBoardService.searchDataLoad("%%" + keyword + "%%");
        if (!freeBoardList.isEmpty()) {
            return new ResponseEntity<>(freeBoardList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

