package com.example.study002.service.Admin;

import com.example.study002.domain.user.User;
import com.example.study002.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

  private final UserRepository userRepository;

  // 모든 사용자 목록을 가져오기
  public List<User> userList(){
    return userRepository.findAll();
  }

  // 지정된 사용자 ID에 해당하는 사용자를 삭제
  @Override
  public void remove(Long id) {
    userRepository.deleteById(id);
  }
}
