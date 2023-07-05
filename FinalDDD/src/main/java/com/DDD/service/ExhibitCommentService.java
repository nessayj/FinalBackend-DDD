package com.DDD.service;


import com.DDD.dto.ExhibitCommentDTO;
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
    public boolean writeComment(Long id, Long exhibitNo, Long starRates, String comment) {
        Optional<Member> member = memberRepository.findById(id);
        Exhibitions exhibitions = exhibitionsRepository.findByExhibitNo(exhibitNo);

        if(member != null && exhibitions != null) {
            ExhibitComment exhibitComment = new ExhibitComment();
            exhibitComment.setMember(member.get());
            exhibitComment.setExhibitions(exhibitions);
            exhibitComment.setStarRates(starRates);
            exhibitComment.setComment(comment);
            exhibitComment.setCommentTime(LocalDateTime.now());

            exhibitCommentRepository.save(exhibitComment);
        }
        try {
            return true;
        } catch (Exception e) {
            System.err.println("오류 발생!! 🤬🤬 : " + e.getMessage());
            return false;
        }
    }
}
