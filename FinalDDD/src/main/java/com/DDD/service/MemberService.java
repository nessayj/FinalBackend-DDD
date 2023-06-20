package com.DDD.service;

import com.DDD.dto.MemberDto;
import com.DDD.entity.Member;
import com.DDD.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    // 생성자 주입
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public boolean signUpMember (String email, String password) {
        Member member = new Member();

        member.setEmail(email);
        member.setPassword(password);

        Member saveMember = memberRepository.save(member);

        log.info("추가된 회원 : " + saveMember.getEmail());
        System.out.println("System.out.추가된 회원  " + member.getEmail());
        System.out.println("System.out.추가된 PW  " + member.getPassword());

        return true;
    }
    public void saveMember(Member member){
        memberRepository.save(member);
    }

}
//    }    public boolean signUpMember (String email, String password){
//        Member member = new Member();
//        member.setEmail(email);
//        member.setPassword(password);
//        Member saveMember = memberRepository.save(member);
//        log.info("추가된 회원 : " + saveMember.getEmail());
//        System.out.println("Systme.out.추가된 회원  " + member.getEmail());
//        System.out.println("Systme.out.추가된 PW  " + member.getPassword());
//        return true;
//    }
