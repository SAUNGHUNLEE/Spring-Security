package com.spring.security.toys.service;

import com.spring.security.toys.DTO.UserDetailsDTO;
import com.spring.security.toys.entity.Member;
import com.spring.security.toys.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("DB에서 이메일을 찾을 수 없습니다.: " + userEmail));

        return new UserDetailsDTO(member); // UserDetailsDTO를 반환
    }


}
