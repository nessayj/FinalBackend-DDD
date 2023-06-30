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
import com.DDD.repository.BookingRepository;
import com.DDD.repository.ExhibitionsRepository;
import com.DDD.repository.MemberRepository;
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

    // 예매, 전시일표시
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //  예매하기
    public boolean bookTicket(String exhibitNo, String id, String bookingDate, String visitDate ) {
        Booking booking = new Booking();

        booking.setBookingDate(LocalDateTime.parse(bookingDate, formatter));
        booking.setVisitDate(LocalDateTime.parse(visitDate, formatter));

        // 회원번호로 회원찾기
        Optional<Member> member = memberRepository.findById(Long.parseLong(id));
        // 찾은 회원 예매에 입력
        booking.setMember(member.get());

        // 전시엔티티 불러와서 넣기
        Exhibitions exhibition = exhibitionsRepository.findByExhibitNo(Long.parseLong(exhibitNo));
        booking.setExhibitions(exhibition);

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
            bookingDTO.setExhibitNo(e.getExhibitions().getExhibitNo());
            bookingDTO.setId(e.getMember().getId());
            bookingDTO.setMemberEmail(e.getMember().getEmail());
            bookingDTO.setExhibitName(e.getExhibitions().getExhibitName());
            bookingDTOS.add(bookingDTO);
        }
        return bookingDTOS;
    }
}
