package com.example.study002.service.Admin;

import com.example.study002.domain.user.User;
import java.util.List;

public interface AdminService {
  // 모든 사용자 목록을 가져오기
  List<User> userList();

  // 지정된 사용자 ID에 해당하는 사용자를 삭제
  void remove(Long id);
}
