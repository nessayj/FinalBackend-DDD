package com.DDD.controller;

import com.DDD.dto.ExhibitionDetailDTO;
import com.DDD.service.ExhibitionApiService;
import com.DDD.service.ExhibitionDetailApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
@Slf4j
@RequestMapping(value = "/exhibitions")
public class ExhibitionsController {
    private final ExhibitionApiService exhibitionApiService;
    private final ExhibitionDetailApiService exhibitionDetailApiService;


    // 전시목록 API 불러오기
    @GetMapping("/list")
    public ResponseEntity<Boolean> getExhibitApiList() {
        boolean result = false;
        String exhibitList = exhibitionApiService.exhibitionListApi();
        result = exhibitionApiService.listFromJsonObj(exhibitList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 해당 전시 상세정보 API 불러오기
    @GetMapping("{seq}")
    public List<ExhibitionDetailDTO> getExhibitDetailApi(@PathVariable Integer seq) {
        String result = exhibitionDetailApiService.ExhibitionDetailApi(seq);
        return exhibitionDetailApiService.detailFromJsonObj(result);
    }

}
