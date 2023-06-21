package com.DDD.service;

import com.DDD.entity.FreeBoard;
import com.DDD.repository.FreeBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j // 출력구문 대신 사용함(System.out.println())
//@RequiredArgsConstructor // 매개변수가 있는 생성자를 자동으로 만들어줌
public class FreeBoardService {
    // 의존성 주입을 통해 빈에 등록된 필드는 불변성이 있어야 하므로 final 선언을 해야함
    private final FreeBoardRepository freeBoardRepository;

    // 게시판 조회
    public FreeBoardService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }

    public List<FreeBoard> getFreeBoardsByCategory(String category) {
        return freeBoardRepository.findByCategory(category);
    }
}
