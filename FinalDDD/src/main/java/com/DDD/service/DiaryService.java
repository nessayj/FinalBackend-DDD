package com.DDD.service;

import com.DDD.dto.DiaryDto;
import com.DDD.dto.FreeBoardDto;
import com.DDD.dto.MemberDto;
import com.DDD.entity.Diary;
import com.DDD.entity.Exhibitions;
import com.DDD.entity.FreeBoard;
import com.DDD.entity.Member;
import com.DDD.repository.DiaryRepository;
import com.DDD.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DiaryService {
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

  // memberId 다이어리 내용 조회
    public List<DiaryDto> getDiaryByMemberId(Long id) { // 프론트엔드에서 member_id를 입력 받음
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + id));
        List<Diary> DiaryList = diaryRepository.findByMemberId(id); // 레파지토리에서 memberId로 조회
        List<DiaryDto> diaryItems= new ArrayList<>(); // 다이어리 data를 저장할 새로운 다이어리를 array list를 생성
        for(Diary item : DiaryList) { // for문을 통해 조회된 내용을 저장
            DiaryDto diaryDto = new DiaryDto();
            diaryDto.setId(item.getId());
            diaryDto.setExhibitions(item.getExhibition());
            diaryDto.setRegDate(item.getRegDate());
            diaryDto.setRateStar(item.getRateStar());
            diaryDto.setComment(item.getComment());
            diaryDto.setMemberId(member.getId());  // getMember().getId() to get memberId

            diaryItems.add(diaryDto); // Add diaryDto to diaryItems
        }
        return diaryItems;

    }

}
