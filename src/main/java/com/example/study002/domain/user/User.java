package com.example.study002.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User 엔티티는 사용자 정보를 나타냅니다.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  // 사용자의 고유 식별자
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 사용자의 로그인 아이디, 중복되지 않아야 함
  @Column(nullable = false, unique = true)
  private String username;

  // 사용자의 이름
  @Column(nullable = false)
  private String name;

  // 사용자의 비밀번호
  @Column(nullable = false)
  private String password;

  // 사용자의 이메일 주소
  private String email;

  // 사용자의 역할 (USER, ADMIN)
  @Column(nullable = false)
  private String role;
}
