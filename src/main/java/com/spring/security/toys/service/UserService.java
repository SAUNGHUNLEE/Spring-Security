package com.spring.security.toys.service;

import com.spring.security.toys.DTO.UserDetailsDTO;
import com.spring.security.toys.entity.Member;
import com.spring.security.toys.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public interface UserService {

    Member login(Member member);

    Member createUser(Member member);

    Member findByEmail(String email);



}
