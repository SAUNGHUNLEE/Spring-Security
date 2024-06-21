package com.spring.security.toys.controller;

import com.spring.security.toys.entity.Member;
import com.spring.security.toys.entity.MemberDTO;
import com.spring.security.toys.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
@Log4j2
public class MemberController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public String login(@RequestBody MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();
        log.info("Received login request for user: " + email);
        System.out.println("password 성공" + password);
        Member foundMember = userService.findByEmail(email);

        if (foundMember == null || !passwordEncoder.matches(password, foundMember.getPassword())) {
            System.out.println("password 실패" + password);
            log.error("Login failed for user: " + email);
            return "Login failed. Check your credentials.";
        }

        log.info("Login successful for user: " + email);
        return "Login successful. Welcome, " + foundMember.getEmail() + "!";
    }

    @PostMapping(value = "/init")
    public String createUser() {
        Member admin = new Member();
        admin.setEmail("admin@naver.com");
        admin.setUsername("관리자");
        admin.setPassword(passwordEncoder.encode("test"));
        admin.setRole(Member.RoleEnum.ROLE_ADMIN);

        log.info("Creating initial admin account: " + admin.getEmail());

        if (userService.createUser(admin) == null) {
            log.error("Failed to create initial admin account.");
            return "Failed to create initial admin account.";
        }

        log.info("Initial admin account created successfully.");
        return "Initial admin account created successfully.";
    }
}
