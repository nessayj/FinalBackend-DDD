package com.DDD.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long bookingNo; // 예매번호(PK)
    private LocalDateTime bookingDate; // 예매일
    private LocalDateTime visitDate; // 방문일
    private Long id; // 회원아이디번호
    private String bookedEmail; // 예매자이메일
    private String bookedName; // 예매자이름
    private String bookedTel; // 예매자연락처
    private Long exhibitNo; // 전시아이디
    private String exhibitName; // 전시회이름
    private LocalDateTime paymentDate; //결제일
    private String paymentMethod; // 결제수단

}
