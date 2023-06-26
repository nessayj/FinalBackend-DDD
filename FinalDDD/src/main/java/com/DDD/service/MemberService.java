package com.DDD.service;

import com.DDD.dto.MemberDto;
import com.DDD.entity.Member;
import com.DDD.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    // 이메일 중복 체크
    public boolean emailDupCk(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    // 닉네임 중복 체크
    public boolean nicknameDupCk(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }

    // 회원 정보 조회
    public List<MemberDto> getMemberInfo(String email) {
        List<MemberDto> memberDtos = new ArrayList<>();
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            MemberDto memberDto = new MemberDto();
            memberDto.setEmail(member.getEmail());
            memberDto.setName(member.getName());
            memberDto.setTel(member.getTel());
            memberDto.setNickname(member.getNickname());
            memberDto.setInstagram(member.getInstagram());
            memberDto.setIntroduce(member.getIntroduce());
            memberDto.setBackgroundImg(member.getBackgroundImg());
            memberDto.setProfileImg(member.getProfileImg());

            memberDtos.add(memberDto);
        }

        return memberDtos;
    }

    // 닉네임 변경
    public boolean newNickname(String email, String nickname) {
        return memberRepository.findByEmail(email)
                .map(member -> {
                    member.setNickname(nickname);
                    Member savedMember = memberRepository.save(member);
                    log.info(savedMember.toString());
                    return true;
                })
                .orElse(false);
    }

    // 이름 변경
    public boolean newName(String email, String name) {
        return memberRepository.findByEmail(email)
                .map(member -> {
                    member.setName(name);
                    Member savedMember = memberRepository.save(member);
                    log.info(savedMember.toString());
                    return true;
                })
                .orElse(false);
    }

    // 연락처 변경
    public boolean newTel(String email, String tel) {
        return memberRepository.findByEmail(email)
                .map(member -> {
                    member.setTel(tel);
                    Member savedMember = memberRepository.save(member);
                    log.info(savedMember.toString());
                    return true;
                })
                .orElse(false);
    }
    // 인스타그램 변경
    public boolean newInstagram(String email, String instagram) {
        return memberRepository.findByEmail(email)
                .map(member -> {
                    member.setInstagram(instagram);
                    Member savedMember = memberRepository.save(member);
                    log.info(savedMember.toString());
                    return true;
                })
                .orElse(false);
    }
    // 소개글 변경
    public boolean newIntroduce(String email, String introduce) {
        return memberRepository.findByEmail(email)
                .map(member -> {
                    member.setIntroduce(introduce);
                    Member savedMember = memberRepository.save(member);
                    log.info(savedMember.toString());
                    return true;
                })
                .orElse(false);
    }

    // 회원 탈퇴
    public Map<String, String> memberDelete(String email, String password){
        Map<String ,String> map = new HashMap<>();
        Optional<Member> member= memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            map.put("memberDelete", "Nothing to delete.");
            return map;
        }
        if(passwordEncoder.matches(password, member.get().getPassword())){
            member.get().setActive(false);
            Member savedMember = memberRepository.save(member.get());
            log.info(savedMember.toString());
            map.put("memberDelete", "Deleted successfully");
            return map;
        }
        map.put("memberDelete", "Delete is Failed");
        return map;
    }



}