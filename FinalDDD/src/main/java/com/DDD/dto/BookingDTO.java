package com.DDD.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long bookingNo; // 예매번호(PK)
    private LocalDateTime bookingDate; // 예매일
    private LocalDateTime visitDate; // 전시방문일
    private Long id; // 회원아이디번호
    private String memberEmail; // 회원이메일
    private Long exhibitNo; // 전시아이디
    private String exhibitName;
}
