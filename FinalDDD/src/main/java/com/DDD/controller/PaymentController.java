package com.DDD.controller;

import com.DDD.constant.PaymentStatus;
import com.DDD.dto.PayConfirmDTO;
import com.DDD.dto.PayReadyDTO;
import com.DDD.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/pay")
public class PaymentController {
    // 카카오페이 결제 요청
    @Autowired
    private PaymentService paymentService;

    // 결제요청
    @PostMapping("/ready") // 처음에 겟매핑에서 포스트로 바꿈
    public PayReadyDTO readyToKakaoPay() {
        return paymentService.kakaoPayReady();
    }

    // 결제성공
    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pg_token) {
        PayConfirmDTO payConfirmDTO = paymentService.ApproveResponse(pg_token);
        return new ResponseEntity<>(payConfirmDTO, HttpStatus.OK);
    }

    // 결제 진행 중 취소
    @GetMapping("/cancel")
    public String payCancel() {
        // 이전 페이지로 리다이렉트
        return "redirect:/previous-page";
    }

    // 결제 실패
    @GetMapping("/fail")
    public String payFail() {
        return "redirect:/previous-page";
    }

}
