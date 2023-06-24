package com.DDD.controller;

import com.DDD.dto.ExhibitionsDto;
import com.DDD.service.ExhibitionApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
@Slf4j
@RequestMapping(value = "/exhibitions")
public class ExhibitionsController {
    private final ExhibitionApiService exhibitionApiService;


    // 전시목록 API 불러오기
    @GetMapping("/list")
    public ResponseEntity<Boolean> getExhibitApiList() {
        boolean result = false;
        String exhibitList = exhibitionApiService.exhibitionListApi();
        result = exhibitionApiService.listFromJsonObj(exhibitList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
