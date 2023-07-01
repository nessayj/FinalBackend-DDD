package com.DDD.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.DDD.dto.BookingDTO;
import com.DDD.entity.Booking;
import com.DDD.entity.Exhibitions;
import com.DDD.entity.Member;
import com.DDD.entity.Payment;
import com.DDD.repository.BookingRepository;
import com.DDD.repository.ExhibitionsRepository;
import com.DDD.repository.MemberRepository;
import com.DDD.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final ExhibitionsRepository exhibitionsRepository;
    private final PaymentRepository paymentRepository;

    // 예매, 전시일표시
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //  예매하기
    public boolean bookTicket(String exhibitNo, String id, String bookingDate, String visitDate, String paymentId ) {
        Booking booking = new Booking();
        Payment payment = new Payment();

        booking.setBookingDate(LocalDateTime.parse(bookingDate, formatter));
        booking.setVisitDate(LocalDateTime.parse(visitDate, formatter));

        // 회원번호로 회원찾기
        Optional<Member> member = memberRepository.findById(Long.parseLong(id));
        // 찾은 회원 예매에 입력
        booking.setMember(member.get());

        // 전시엔티티 불러와서 넣기
        Exhibitions exhibition = exhibitionsRepository.findByExhibitNo(Long.parseLong(exhibitNo));
        booking.setExhibitions(exhibition);

        // 예약과 동시에 결제에도 입력
        payment.setPaymentId(Long.parseLong(paymentId));
        booking.setPayment(payment);

        try {
            bookingRepository.save(booking);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 예매조회
    public List<BookingDTO> FindTicket(String id) {
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        List<Booking> bookingList = bookingRepository.findAll();
        for(Booking e : bookingList) {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setBookingNo(e.getBookingId());
            bookingDTO.setId(e.getMember().getId());
            bookingDTO.setExhibitNo(e.getExhibitions().getExhibitNo());
            bookingDTO.setExhibitName(e.getExhibitions().getExhibitName());
            bookingDTO.setBookingDate(e.getBookingDate());
            bookingDTO.setVisitDate(e.getVisitDate());
            bookingDTOS.add(bookingDTO);
        }
        return bookingDTOS;
    }
}
