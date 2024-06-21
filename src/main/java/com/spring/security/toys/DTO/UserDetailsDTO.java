package com.spring.security.toys.DTO;

import com.spring.security.toys.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class UserDetailsDTO implements UserDetails {

    private final Member member;
    private final Collection<? extends GrantedAuthority> authorities;


    public UserDetailsDTO(Member member) {
        this.member = member;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(member.getRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 여기에 필요한 로직을 추가할 수 있습니다.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 여기에 필요한 로직을 추가할 수 있습니다.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 여기에 필요한 로직을 추가할 수 있습니다.
    }

    @Override
    public boolean isEnabled() {
        return member.isEnabled();
    }
}
