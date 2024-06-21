package com.spring.security.toys.service;

import com.spring.security.toys.DTO.UserDetailsDTO;
import com.spring.security.toys.entity.Member;
import com.spring.security.toys.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final MemberRepository memberRepository;

    @Override
    public Member login(Member member) {
        return memberRepository.findByUserEamilAndUserPw(member.getEmail(),member.getPassword());
    }

    @Override
    public Member createUser(Member member) {


        return memberRepository.save(member);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("이메일 없음"));
    }
}
