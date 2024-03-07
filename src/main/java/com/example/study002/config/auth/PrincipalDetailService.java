package com.example.study002.config.auth;

import com.example.study002.domain.user.User;
import com.example.study002.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 사용자 이름을 기반으로 사용자 정보를 가져와 UserDetails 객체로 반환
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름을 통해 데이터베이스에서 사용자 정보 조회
        User user=userRepository.findByUsername(username);
        if(user==null){
            // 사용자가 없을 경우 예외 발생
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // 조회된 사용자 정보를 UserDetails 객체로 변환하여 반환
        PrincipalDetails puser = new PrincipalDetails(user);
        return puser;
    }
}
