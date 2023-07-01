package com.DDD.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@RequiredArgsConstructor
public class PayConfirmDTO {
    // 결제 승인요청
    // 요청고유번호, 결제고유번호, 가맹점코드, 정기결제CID로 단건결제 요청시 발급
    private String aid, tid, cid, sid;
    // 가맹점 주문번호, 가맹점 회원 id, 결제수단(CARD/MONEY)
    private String partner_order_id, partner_user_id, payment_method_type;
    // 결제 금액 정보 객체
    private PayAmountDTO amount;
    // 결제 상세 정보 객체(카드일경우)
    private PayCardDTO card_info;
    // 상품명, 상품코드, 결제 승인요청에 대해 저장한 값
    private String item_name, item_code, payload;
    // 상품수량
    private Integer quantity;
    //결제 준비 요청시각, 결제 승인 시각
    private LocalDateTime created_at, approved_at;
}
