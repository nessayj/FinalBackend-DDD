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
        // DTO 에서 작성자 정보 가져오기
       Optional<Member> optionalMember = memberRepository.findByNickname(author);

        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("해당 닉네임을 가진 멤버를 찾을 수 없습니다.");
        }

        // Optional 에서 멤버 가져오기
        Member member = optionalMember.get();

        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setMember(member);
        freeBoard.setCategory(category);
        freeBoard.setRegion(region);
        freeBoard.setTitle(title);
        freeBoard.setImage(image);
        freeBoard.setContents(contents);

        freeBoardRepository.save(freeBoard);
        return true;
    }

          // 게시글 작성 재체크
//        public void createBoards(FreeBoardDto dto) {
//        Member member = memberRepository.findByNickname(dto.getAuthor()).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없습니다."));
//        FreeBoard freeBoard = FreeBoard.builder()
//                .dto(dto)
//                .member(member)
//                .build();
//        freeBoardRepository.save(freeBoard);


//    // 게시글 작성(1차 작업)
//    public boolean createBoards(String author, String category, String region, String title, String image, String contents) {
//        Optional<Member> optionalMember = memberRepository.findByNickname(author);
//
////        if (optionalMember.isEmpty()) {
////            throw new UsernameNotFoundException("해당 닉네임을 가진 멤버를 찾을 수 없습니다.");
////        }
//        // Optional 에서 멤버 가져오기
//        Member member = optionalMember.get();
//
//        FreeBoard freeBoard = new FreeBoard();
//        freeBoard.setAuthor(member);
//        freeBoard.setCategory(category);
//        freeBoard.setRegion(region);
//        freeBoard.setTitle(title);
//        freeBoard.setImage(image);
//        freeBoard.setContents(contents);
//
//        freeBoardRepository.save(freeBoard);
//        return true;
//    }

    // 게시글 상세조회
    public FreeBoardDto selectBoardOne(Long boardNo) {
        FreeBoard freeBoard = freeBoardRepository.findById(boardNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다."));

        // 조회수 증가
        if (freeBoard.getViews() == null) {
            freeBoard.setViews(1);
        } else {
            freeBoard.setViews(freeBoard.getViews() + 1);
        }
        freeBoardRepository.save(freeBoard);


        FreeBoardDto freeboardDto = new FreeBoardDto();
        freeboardDto.setBoardNo(freeBoard.getBoardNo());
        freeboardDto.setAuthor(freeBoard.getMember().getNickname());
        freeboardDto.setEmail(freeBoard.getMember().getEmail()); // 회원 정보 할당
        freeboardDto.setCategory(freeBoard.getCategory());
        freeboardDto.setRegion(freeBoard.getRegion());
        freeboardDto.setTitle(freeBoard.getTitle());
        freeboardDto.setContents(freeBoard.getContents());
        freeboardDto.setImage(freeBoard.getImage());
        freeboardDto.setWriteDate(freeBoard.getWriteDate());

        // 프로필 이미지 설정 추가
        freeboardDto.setProfileImg(freeBoard.getMember().getProfileImg());



        return freeboardDto;
    }



    // 게시글 수정(최종) + 작성자 정보 예외처리 추가
    public boolean updateBoards(Long boardNo, FreeBoardDto freeBoardDto, Long id) {
        FreeBoard freeBoard = freeBoardRepository.findById(boardNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다."));

        System.out.println("freeBoard.getAuthor().getNickname(): " + freeBoard.getMember().getNickname()); // 닉네임 값 제대로 들어오는지 확인

        // 작성자 확인
        if (!freeBoard.getMember().getId().equals(id)) {
            throw new IllegalArgumentException("작성자만 게시글을 수정할 수 있습니다.");
        }

        // 게시글 정보 업데이트
        freeBoard.setCategory(freeBoardDto.getCategory());
        freeBoard.setRegion(freeBoardDto.getRegion());
        freeBoard.setTitle(freeBoardDto.getTitle());
        freeBoard.setImage(freeBoardDto.getImage());
        freeBoard.setContents(freeBoardDto.getContents());

        // 게시글 저장
        freeBoardRepository.save(freeBoard);

        return true;
    }


    // 게시글 삭제(최종)
    public boolean deleteBoards(Long boardNo, Long id) {
        FreeBoard freeBoard = freeBoardRepository.findById(boardNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다."));

        // 작성자 확인
        if (!freeBoard.getMember().getId().equals(id)) {
            throw new IllegalArgumentException("작성자만 게시글을 삭제할 수 있습니다.");
        }

        freeBoardRepository.delete(freeBoard);
        return true;
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

//            if (freeBoard != null && freeBoardDto != null) { // 조회수 수정사항
//                freeBoardDto.setViews(freeBoardDto.getViews());
//            }

            freeBoardDto.setViews(freeBoard.getViews()); // 조회수 현재값으로 재수정
            System.out.println("조회수: " + freeBoard.getViews());


            Member author = freeBoard.getMember(); // nickName 을 fk로 갖고 오기 위해
            if (author != null) {
                MemberDto memberDto = MemberDto.fromMember(author);
                freeBoardDto.setAuthor(memberDto.getNickname());
            }
            freeBoards.add(freeBoardDto);
        }
        return freeBoards;

    }
}
