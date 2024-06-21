package com.spring.security.toys.config;

import com.spring.security.toys.filter.CustomAuthenticationFilter;
import com.spring.security.toys.filter.CustomAuthenticationProvider;
import com.spring.security.toys.filter.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManager authenticationManager) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/index").authenticated()
                .requestMatchers("/init").authenticated()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().permitAll()
        );


        /* security에서 제공하는 로그인 폼
        http.formLogin(form -> form
                        .loginPage("/user/login")
                        .successHandler(customLoginSuccessHandler())
                        .failureUrl("/user/login?error=true")
                        .permitAll()
                );*/


        //개인 로그인 폼 사용시 + 커스텀인증필터 사용
        http.formLogin(form -> form.disable());
        http.addFilterBefore(customAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        return customAuthenticationFilter;
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

  /*  @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder);
    }*/

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);

        // CustomAuthenticationProvider를 추가로 설정
        auth.authenticationProvider(customAuthenticationProvider);
    }
}
