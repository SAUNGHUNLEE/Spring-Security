package com.spring.security.toys.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동증가
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Enumerated(EnumType.STRING) // Enum 타입을 String으로 매핑
    @Column(name = "role", length = 50)
    private RoleEnum role;

    public enum RoleEnum  {
        ROLE_USER,
        ROLE_ADMIN;
    }

}
