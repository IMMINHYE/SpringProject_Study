package com.example.study002.config.auth;

import com.example.study002.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {
    // 로그인 사용자 정보를 가짐
    private User user;

    public PrincipalDetails(User user){
        this.user=user;
    }

    // 사용자의 권한 목록을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection=new ArrayList<GrantedAuthority>();
        // 사용자의 권한을 GrantedAuthority 객체로 변환하여 리스트에 추가
        collection.add(()->{return user.getRole();});
        return collection;
    }

    // 사용자의 비밀번호 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 사용자의 이름 반환
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정의 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정의 잠김 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 자격 증명(비밀번호) 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정의 활성 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
