package com.DDD.service;

import com.DDD.entity.Member;
import com.DDD.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 로그인 체크
    public boolean loginCheck(String email, String password) {
        List<Member> memberInfoList = memberRepository.findByEmailAndPassword(email, password);

        return !memberInfoList.isEmpty();
//        for(Member info : memberInfoList) {
//            return true;
//        }
//        return false;
    }

    // 회원가입
    public boolean signupMember(String email, String password, String nickname, String tel, String name, String instagram) {
        Member member = new Member();

        member.setEmail(email);
        member.setName(name);
        member.setPassword(password);
        member.setTel(tel);
        member.setNickname(nickname);
        member.setInstagram(instagram);

        Member saveMember = memberRepository.save(member);

        log.info("추가된 회원 : " + saveMember.getEmail());
        System.out.println("System.out.추가된 회원  " + member.getEmail());
        System.out.println("System.out.추가된 PW  " + member.getPassword());

        return true;
    }
    // 이메일 중복 체크
    public boolean emailDupCk(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    // 닉네임 중복 체크
    public boolean nicknameDupCk(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }

    // 닉네임 변경
    public boolean newNickname(long id, String nickname){
        Optional<Member> optionalMember = memberRepository.findById(id);

        if(optionalMember.isPresent()){
            Member editMember = optionalMember.get();
            editMember.setNickname(nickname);

            Member savedMember = memberRepository.save(editMember);

            log.info(savedMember.toString());
            return true;

        } else {
            return false;
        }
    }

}