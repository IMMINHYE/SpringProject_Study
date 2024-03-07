package com.example.study002.config.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class UserLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

  // 로그인 실패 시 호출되는 메서드
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {

    String errorMessage;
    // 예외에 따른 오류 메시지 설정
    if (exception instanceof BadCredentialsException) {
      errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
    } else if (exception instanceof InternalAuthenticationServiceException) {
      errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
    } else if (exception instanceof UsernameNotFoundException) {
      errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
    } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
      errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
    } else {
      errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
    }

    // 한글 오류 메시지를 URL 인코딩하여 설정
    errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
    setDefaultFailureUrl("/user/login?error=true&exception=" + errorMessage);

    // 부모 클래스의 메서드를 호출하여 실패 처리 진행
    super.onAuthenticationFailure(request, response, exception);
  }
}