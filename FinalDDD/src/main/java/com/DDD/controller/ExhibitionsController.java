package com.DDD.controller;

import com.DDD.dto.ExhibitionsDto;
import com.DDD.service.ExhibitionApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Slf4j
@RequestMapping("/exhibitions")
public class ExhibitionsController {
    private final ExhibitionApiService exhibitionApiService;

    // 전시목록 API 불러오기
    @GetMapping("/list")
    public ResponseEntity<Boolean> getExhibitionsList() {
        String result = exhibitionApiService.ExhibitionListApi();
        boolean isSuccess = exhibitionApiService.listFromJsonObj(result);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

}
