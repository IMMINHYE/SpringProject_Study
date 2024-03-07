package com.example.study002.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * LoginHistory 엔티티는 사용자의 로그인 이력을 나타냅니다.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistory {
  // 로그인 이력의 고유 식별자
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 로그인한 사용자와의 관계 설정
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  // 로그인 시간
  private LocalDateTime loginTime;
}
