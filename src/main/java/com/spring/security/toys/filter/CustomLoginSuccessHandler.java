package com.spring.security.toys.filter;

import com.spring.security.toys.entity.Member;
import com.spring.security.toys.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Log4j2
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

   @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
       SecurityContextHolder.getContext().setAuthentication(authentication);
       System.out.println(("로그인 성공: " + authentication.getName()));
       System.out.println(("현재 인증 정보: " + SecurityContextHolder.getContext().getAuthentication()));
       response.sendRedirect("/index");
       System.out.println("로그인 성공");
   }
}
