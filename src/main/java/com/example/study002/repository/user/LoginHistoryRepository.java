package com.example.study002.repository.user;

import com.example.study002.domain.user.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
  // 로그인 기록
  List<LoginHistory> findByLoginTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}