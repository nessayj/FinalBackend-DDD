package com.DDD.service;

import com.DDD.dto.FreeBoardDto;
import com.DDD.dto.MemberDto;
import com.DDD.entity.FreeBoard;
import com.DDD.entity.Member;
import com.DDD.repository.FreeBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j // 출력구문 대신 사용함(System.out.println())
//@RequiredArgsConstructor // 매개변수가 있는 생성자를 자동으로 만들어줌
public class FreeBoardService {
    // 의존성 주입을 통해 빈에 등록된 필드는 불변성이 있어야 하므로 final 선언을 해야함
    private final FreeBoardRepository freeBoardRepository;


    public FreeBoardService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }

    // 카테고리별 게시판 조회
    public List<FreeBoardDto> getFreeBoardsByCategory(String category) { // 카테고리별로 가져오기 위해
        List<FreeBoardDto> freeBoards = new ArrayList<>();
        List<FreeBoard> freeBoardList = freeBoardRepository.findByCategory(category);
        for(FreeBoard freeBoard : freeBoardList) {
            FreeBoardDto freeBoardDto = new FreeBoardDto();
            freeBoardDto.setBoardNo(freeBoard.getBoardNo());
            freeBoardDto.setCategory(freeBoard.getCategory());
            freeBoardDto.setTitle(freeBoard.getTitle());
            freeBoardDto.setViews(freeBoard.getViews());
            freeBoardDto.setImage(freeBoard.getImage()); // 추가사항(이미지)
//            freeBoardDto.setContents(freeBoard.getContents()); 내용 제외
            freeBoardDto.setWriteDate(freeBoard.getWriteDate());


            Member author = freeBoard.getAuthor(); // nickName 을 fk로 갖고오기 위해
            if (author != null) {
                MemberDto memberDto = MemberDto.fromMember(author);
                freeBoardDto.setAuthor(memberDto.getNickname());
            } // 여기까지
            freeBoards.add(freeBoardDto);
        }
        return freeBoards;
    }
}
