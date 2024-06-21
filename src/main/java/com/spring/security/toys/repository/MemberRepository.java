package com.spring.security.toys.repository;

import com.spring.security.toys.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    // 이메일로 찾기
    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT * FROM member where email = :email AND password = :password", nativeQuery = true)
    Member findByUserEamilAndUserPw(@Param("email") String email,@Param("password") String password);
}
