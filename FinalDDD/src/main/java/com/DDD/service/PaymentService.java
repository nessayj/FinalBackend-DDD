package com.DDD.service;

import com.DDD.constant.PaymentStatus;
import com.DDD.dto.KakaoApproveResponse;
import com.DDD.dto.PayReadyDTO;
import com.DDD.entity.Exhibitions;
import com.DDD.entity.Member;
import com.DDD.entity.Payment;
import com.DDD.repository.ExhibitionsRepository;
import com.DDD.repository.MemberRepository;
import com.DDD.repository.PaymentRepository;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private PayReadyDTO payReadyDTO;
    // 결제정보 db저장
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final ExhibitionsRepository exhibitionsRepository;
    // 카카오 어드민키
    @Value("${api.kakaoAdminKey}")
    private String adminKey;

    // 날짜시간
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    // 결제 준비
    public PayReadyDTO kakaoPayReady(String id, String exhibitNo, String quantity, String totalPrice) {
        // 전시회 정보조회
        Exhibitions exhibitions = exhibitionsRepository.findByExhibitNo(Long.parseLong(exhibitNo));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // 가맹점코드(필수), 테스트용은 "TC0ONETIME" 고정
        params.add("cid", "TC0ONETIME");
        // 가맹점 주문번호(필수)
        params.add("partner_order_id", ":DDD");
        // 가맹점 회원아이디(필수)
        params.add("partner_user_id", id);
        // 상품명(필수) 변수받아서 변화하는값
        params.add("item_name", exhibitions.getExhibitName());
        // 상품코드(필수는 아니나 상품명 찾기 용이)
        params.add("item_code", exhibitNo);
        // 상품수량(필수)
        params.add("quantity", quantity);
        // 상품총액(필수)
        params.add("total_amount", totalPrice);
        // 상품비과세 금액(필수)
        params.add("tax_free_amount", "0");
        // 성공 시 redirect url => 결제완료페이지와 연결해야함
//        String approvalUrl = "http://localhost:8111/pay/success?id=" + id;
        params.add("approval_url", "http://localhost:3000/");
        // 취소 시  url
        params.add("cancel_url", "http://localhost:8111/pay/cancel");
        // 실패 시  url
        params.add("fail_url", "http://localhost:8111/pay/fail");

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

    // 카카오가 요구하는 헤더값
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
    public KakaoApproveResponse ApproveResponse(String pg_Token, String id) {

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", "TC0ONETIME");
        parameters.add("tid", payReadyDTO.getTid());
        parameters.add("partner_order_id", ":DDD");
        parameters.add("partner_user_id", id);
        parameters.add("pg_token", pg_Token);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponse.class);

        // 결제정보엔티티에 저장
        if (approveResponse != null && approveResponse.getAid() !=null) {
            Payment payment = new Payment();
            payment.setPaymentType("카카오페이");
            payment.setPaidPrice(approveResponse.getAmount().getTotal());
            payment.setPaymentStatus(PaymentStatus.결제완료);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentCnt(approveResponse.getQuantity());

            Member member = memberRepository.findById(Long.parseLong(id)).orElse(null);
            payment.setMember(member);

            paymentRepository.save(payment);
        }

        return approveResponse;
    }
}
