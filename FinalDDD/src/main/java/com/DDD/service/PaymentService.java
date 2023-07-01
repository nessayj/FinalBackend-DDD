package com.DDD.service;

import com.DDD.dto.PayConfirmDTO;
import com.DDD.dto.PayReadyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    // 카카오 어드민키
    @Value("${api.kakaoAdminKey}")
    private String adminKey;

    // 카카오페이 api 호출주소
    private String url = "http://kapi.kakao.com/v1/payment";

    private PayReadyDTO payReadyDTO;
    private PayConfirmDTO payConfirmDTO;

    // 결제 준비 요청 단계
    public String PayReady(String price) {
        RestTemplate restTemplate = new RestTemplate();
        // 카카오가 요구한 값을 서버로 요청할 HEADER
        HttpHeaders headers = new HttpHeaders();
        // 카카오 인증키
        headers.add("Authorization", "kakaoAK" + adminKey);;
        // 넘겨줄 타입
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오가 요구한 결제 요청값을 담아주는 바디
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // 가맹점 코드(필수) 테스트용은 "TC0ONETIME" 고정
        params.add("cid", "TC0ONETIME");
        // 가맹점 주문번호
        params.add("partner_order_id", ":DDD001");
        // 가맹점 회원아이디
        params.add("partner_user_id", ":DDD");
        // 상품명
        params.add("item_name", ":DDD");
        // 상품코드
        params.add("item_code", "exhibitNo");
        // 상품수량
        params.add("quantity", "1");
        // 상품총액
        params.add("total_amount", price);
        //상품 비과세 금액
        params.add("tax_free_amount", "0");
        // 결제 성공시 url -> 결제 완료 페이지로 이동
        params.add("approval_url", "http://localhost:3000/");
        // 결제 취소시 url
        params.add("cancel_url", "http://localhost:3000/");
        // 결제 실패시 url
        params.add("fail_url", "http://localhost:3000/");

        // 하나의 map 안에 header와 parameter값을 담음
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        payReadyDTO = restTemplate.postForObject(url + "/ready", body, PayReadyDTO.class);

        return payReadyDTO.getNext_redirect_pc_url();
    }

    // 결제 승인 단계
    public PayConfirmDTO PayInfo(String pg_token) {
        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");


        // 카카오가 요구한 결제 승인 요청값을 담아줄 바디
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid","TC0ONETIME");
        // 결제 요청단계에서 받은 tid 넘겨줌
        params.add("tid", payReadyDTO.getTid());
        params.add("partner_order_id", ":DDD001");
        params.add("partner_user_id", ":DDD");
        // 결제 승인이 되면 생성되는 토큰 넘김
        params.add("pg_token", pg_token);
        params.add("total_amount", "totalPrice");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            payConfirmDTO = restTemplate.postForObject(url + "/approve", body, PayConfirmDTO.class);
            return payConfirmDTO;
        } catch (Exception e) {
            e.printStackTrace();
        } return null;
    }
}
