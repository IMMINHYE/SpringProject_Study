package com.example.study002.config.handler;

import com.example.study002.domain.user.LoginHistory;
import com.example.study002.domain.user.User;
import com.example.study002.repository.user.LoginHistoryRepository;
import com.example.study002.repository.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Log4j2

public class UserLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final LoginHistoryRepository loginHistoryRepository;
  private final UserRepository userRepository;

  // 로그인 성공 시 호출되는 메서드
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    // 인증 정보에서 사용자 정보를 가져옴
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername());

    // 로그인 이력 생성 및 저장
    LoginHistory loginHistory = new LoginHistory();
    loginHistory.setUser(user);
    loginHistory.setLoginTime(LocalDateTime.now());
    loginHistoryRepository.save(loginHistory);

    // 홈 페이지로 리다이렉션
    response.sendRedirect("/");
  }
}
