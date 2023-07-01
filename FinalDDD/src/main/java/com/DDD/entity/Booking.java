package com.DDD.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@Table(name="booking_ticket")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId; // 예매번호(PK)
    @ManyToOne
    @JoinColumn(name = "exhibit_no") // 전시번호(FK)
    private Exhibitions exhibitions;
    @OneToOne
    @JoinColumn(name = "member_id") // 회원번호(FK)
    private Member member;
    @ManyToOne
    @JoinColumn(name="payment_id")
    private Payment payment;
    @Column(name = "booking_date")
    private LocalDateTime bookingDate; // 예매일
    @Column(name = "visit_date")
    private LocalDateTime visitDate; // 전시관람일

    public Member getMember() {
        return member;
    }

    public Exhibitions getExhibitions() {
        return exhibitions;
    }

    public Payment getPayment() {
        return payment;
    }


}