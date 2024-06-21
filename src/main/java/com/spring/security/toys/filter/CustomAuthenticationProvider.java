package com.spring.security.toys.filter;

import com.spring.security.toys.DTO.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        //authenticationFilter에서 생성된 토큰으로부터 아이디 부터 조회후 그다음 비번 조회
        String email = token.getName();
        String password = (String)token.getCredentials();

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) userDetailsService.loadUserByUsername(email);
        if(!bCryptPasswordEncoder.matches(password,userDetailsDTO.getPassword())){
            throw new BadCredentialsException(userDetailsDTO.getUsername() + "비밀번호가 틀렸습니다." );

        }


        return new UsernamePasswordAuthenticationToken(userDetailsDTO,password,userDetailsDTO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
