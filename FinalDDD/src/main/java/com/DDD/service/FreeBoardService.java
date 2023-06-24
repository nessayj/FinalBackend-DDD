package com.DDD.service;

import com.DDD.dto.FreeBoardDto;
import com.DDD.dto.MemberDto;
import com.DDD.entity.FreeBoard;
import com.DDD.entity.Member;
import com.DDD.repository.FreeBoardRepository;
import com.DDD.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j // 출력구문 대신 사용함(System.out.println())
//@RequiredArgsConstructor // 매개변수가 있는 생성자를 자동으로 만들어줌
public class FreeBoardService {
    // 의존성 주입을 통해 빈에 등록된 필드는 불변성이 있어야 하므로 final 선언을 해야함
    private final FreeBoardRepository freeBoardRepository;
    private final MemberRepository memberRepository; // 게시물 작성 위해 멤버 추가


    public FreeBoardService(FreeBoardRepository freeBoardRepository, MemberRepository memberRepository) { // 게시판 서비스 클래스의 생성자

        this.freeBoardRepository = freeBoardRepository;
        this.memberRepository = memberRepository;
    }

    // 게시글 작성
    public boolean createBoards(String author, String category, String region, String title, String image, String contents) {
        Optional<Member> optionalMember = memberRepository.findByNickname(author);

//        if (optionalMember.isEmpty()) {
//            throw new UsernameNotFoundException("해당 닉네임을 가진 멤버를 찾을 수 없습니다.");
//        }

        Member member = optionalMember.get();

        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setAuthor(member);
        freeBoard.setCategory(category);
        freeBoard.setRegion(region);
        freeBoard.setTitle(title);
        freeBoard.setImage(image);
        freeBoard.setContents(contents);

        freeBoardRepository.save(freeBoard);
        return true;
    }

    // 게시글 상세조회
    public FreeBoardDto selectBoardOne(Long boardNo) {
        FreeBoard freeBoard = freeBoardRepository.findById(boardNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다."));


        FreeBoardDto freeboardDto = new FreeBoardDto();
        freeboardDto.setBoardNo(freeBoard.getBoardNo());
        freeboardDto.setAuthor(freeBoard.getAuthor().getNickname());
        freeboardDto.setCategory(freeBoard.getCategory());
        freeboardDto.setRegion(freeBoard.getRegion());
        freeboardDto.setTitle(freeBoard.getTitle());
        freeboardDto.setContents(freeBoard.getContents());
        freeboardDto.setImage(freeBoard.getImage());
        freeboardDto.setWriteDate(freeBoard.getWriteDate());



        if (freeBoard != null && freeboardDto != null) { // 조회수 설정 구간
            freeboardDto.setViews(freeboardDto.getViews());
        }

        return freeboardDto;
    }



    // 카테고리별 게시판 조회
    public List<FreeBoardDto> getFreeBoardsByCategory(String category) { // 카테고리별로 가져오기 위해
        List<FreeBoardDto> freeBoards = new ArrayList<>();
        List<FreeBoard> freeBoardList = freeBoardRepository.findByCategory(category);
        for(FreeBoard freeBoard : freeBoardList) {
            FreeBoardDto freeBoardDto = new FreeBoardDto();
            freeBoardDto.setBoardNo(freeBoard.getBoardNo());
            freeBoardDto.setCategory(freeBoard.getCategory());
            freeBoardDto.setRegion(freeBoard.getRegion()); // 지역카테고리 추가
            freeBoardDto.setTitle(freeBoard.getTitle());
            freeBoardDto.setImage(freeBoard.getImage()); // 추가사항(이미지)
//            freeBoardDto.setContents(freeBoard.getContents()); 내용 제외
            freeBoardDto.setWriteDate(freeBoard.getWriteDate());

            if (freeBoard != null && freeBoardDto != null) { // 조회수 수정사항
                freeBoardDto.setViews(freeBoardDto.getViews());
            }


            Member author = freeBoard.getAuthor(); // nickName 을 fk로 갖고 오기 위해
            if (author != null) {
                MemberDto memberDto = MemberDto.fromMember(author);
                freeBoardDto.setAuthor(memberDto.getNickname());
            }
            freeBoards.add(freeBoardDto);
        }
        return freeBoards;

    }
}
