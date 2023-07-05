package com.DDD.service;


import com.DDD.entity.ExhibitComment;
import com.DDD.entity.Exhibitions;
import com.DDD.entity.Member;
import com.DDD.repository.ExhibitCommentRepository;
import com.DDD.repository.ExhibitionsRepository;
import com.DDD.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ExhibitCommentService {

    private final MemberRepository memberRepository;
    private final ExhibitionsRepository exhibitionsRepository;
    private final ExhibitCommentRepository exhibitCommentRepository;

    // 한줄 평 작성
    public boolean writeComment(String id, String exhibitNo, String starRates, String comment) {
        try {

            // 회원번호로 회원찾기
            Optional<Member> member = memberRepository.findById(Long.parseLong(id));
            if (member.isEmpty()) {
                throw new IllegalArgumentException("없는 회원 ID 입니다!");
            }

            Exhibitions exhibition = exhibitionsRepository.findByExhibitNo(Long.parseLong(exhibitNo));
            if (exhibition == null) {
                throw new IllegalArgumentException("없는 전시번호입니다!");
            }

            ExhibitComment exhibitComment = new ExhibitComment();
            exhibitComment.setMember(member.get());
            exhibitComment.setExhibitions(exhibition);
            exhibitComment.setStarRates(Double.parseDouble(starRates));
            exhibitComment.setComment(comment);
            exhibitComment.setCommentTime(LocalDateTime.now());

            exhibitCommentRepository.save(exhibitComment);

            return true; // 작성 성공 시 true 반환


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("오류 발생!! 🤬🤬 : " + e.getMessage());
            return false; // 예외 발생 시 false 반환
        }
    }


}
