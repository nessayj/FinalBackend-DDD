package com.DDD.controller;

import com.DDD.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/pay")
public class PaymentController {
    // 카카오페이 결제 요청
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String PayReady(@RequestParam String price) {
        return "redirect: " + paymentService.PayReady(price);
    }

    // 카카오페이 결제 승인요청
    @GetMapping("/success")
    public Model paySuccess(@RequestParam("pg_token") String pg_token, Model model) {
        // 결제 정보를 모델에 저장
        model.addAttribute("info", paymentService.PayInfo(pg_token));
        return model;
    }

    // 카카오페이 결제 취소시 실행 url
    @GetMapping("/cancel")
    public String payCancel() {
        return "redirect:/";
    }
}
