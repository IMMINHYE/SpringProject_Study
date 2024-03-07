package com.example.study002.config;

import com.example.study002.config.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final PrincipalDetailService principalDetailService;
  private final AuthenticationFailureHandler userLoginFailHandler;
  private final AuthenticationSuccessHandler userLoginSuccessHandler;

  // 정적 리소스를 위한 필터 제외 설정
  @Bean
  public WebSecurityCustomizer configure() {
    return (web) -> web.ignoring()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  // Security Filter Chain 설정
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
        .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable())
        .authorizeHttpRequests(authorizeHttpRequestConfigurer -> authorizeHttpRequestConfigurer
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/", "/user/login", "/signup","/user/admin","/checkDuplicate",
                    "/community/list","/community/view","/media/medialist",
                    "/study/studylist", "/study/view").permitAll()
                .requestMatchers("/user/admin").hasAuthority("ADMIN")
                .requestMatchers("/","/user/editPassword").hasAuthority("USER")
                .anyRequest().authenticated()
        )// 인증별 권한 설정
        .formLogin(formLoginConfigurer -> formLoginConfigurer
            .loginPage("/user/login")
            .loginProcessingUrl("/loginProcess")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(userLoginSuccessHandler)
            .failureHandler(userLoginFailHandler)
        )
        .logout(logoutConfigurer -> logoutConfigurer
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
        )

        .build();
  }

  // BCryptPasswordEncoder 빈 등록
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


  // AuthenticationManager 빈 등록
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http,
                                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                                     UserDetailsService userDetailsService)
      throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService)
        .passwordEncoder(bCryptPasswordEncoder);
    return authenticationManagerBuilder.build();
  }
}
