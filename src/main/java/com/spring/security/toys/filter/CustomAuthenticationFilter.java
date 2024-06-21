package com.spring.security.toys.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.toys.entity.Member;
import com.spring.security.toys.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Log4j2
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/login");
        System.out.println("커스텀 필터로 인해 로그인 페이지 이동");
    }




    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authRequest = null;

        try{
            Map<String,String> requestMap = objectMapper.readValue(request.getInputStream(),Map.class);
            String email = requestMap.get("email");
            String password = requestMap.get("password");

            log.info("입력한 계정 이메일: " + email);


            authRequest = new UsernamePasswordAuthenticationToken(email,password);
            setDetails(request, authRequest);

        } catch (IOException e) {
            log.error("JSON 파싱 에러", e);
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }



}
