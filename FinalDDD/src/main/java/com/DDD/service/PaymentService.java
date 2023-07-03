package com.DDD.service;

import com.DDD.dto.PayConfirmDTO;
import com.DDD.dto.PayReadyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private PayReadyDTO payReadyDTO;
    // 카카오 어드민키
    @Value("${api.kakaoAdminKey}")
    private String adminKey;


    // 결제 준비2
    public PayReadyDTO kakaoPayReady() {

        // 카카오페이 요청 양식
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // 가맹점코드(필수), 테스트용은 "TC0ONETIME" 고정
        params.add("cid", "TC0ONETIME");
        // 가맹점 주문번호(필수)
        params.add("partner_order_id", "가맹점 주문 번호");
        // 가맹점 회원아이디(필수)
        params.add("partner_user_id", "가맹점 회원 ID");
        // 상품명(필수) 변수받아서 변화하는값
        params.add("item_name", "exhibitName");
        // 상품코드(필수는 아니나 상품명을 더 쉽게 찾기위해?필요할것같..?)
        params.add("item_code", "21239" );
        // 상품수량(필수)
        params.add("quantity", "1");
        // 상품총액(필수)
        params.add("total_amount", "5000");
        // 상품비과세 금액(필수)
        params.add("vat_amount", "0");
        // 성공 시 redirect url => 결제완료페이지와 연결해야함
        params.add("approval_url", "http://localhost:3000/");
        // 취소 시  url
        params.add("cancel_url", "http://localhost:3000");
        // 실패 시  url
        params.add("fail_url", "http://localhost:3000");

        // 하나의 맵안에 헤더와 파라미터값 담음
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, this.getHeaders());

        // 외부에 보낼 url 카카오가 요구한 값을 서버로 요청
        RestTemplate restTemplate = new RestTemplate();

        payReadyDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                PayReadyDTO.class);

        return payReadyDTO;
    }

    // 카카오가 요구하는 헤더값 따로다시
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        // 카카오인증키
        String auth = "KakaoAK " + adminKey;

        httpHeaders.set("Authorization", auth);
        // 카카오에게 넘겨줄 타입
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    // 결제 승인 단계
    public PayConfirmDTO ApproveResponse(String pg_token) {

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

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, this.getHeaders());


        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        try {
           PayConfirmDTO payConfirmDTO = restTemplate.postForObject(
                   "http://kapi.kakao.com/v1/payment/approve",
                   requestEntity,
                   PayConfirmDTO.class);

           return payConfirmDTO;
        } catch (Exception e) {
            e.printStackTrace();
        } return null;
    }
}