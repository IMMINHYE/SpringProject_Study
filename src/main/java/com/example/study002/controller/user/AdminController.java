package com.example.study002.controller.user;

import com.example.study002.domain.user.LoginHistory;
import com.example.study002.domain.user.User;
import com.example.study002.repository.user.LoginHistoryRepository;
import com.example.study002.repository.user.UserRepository;
import com.example.study002.service.Admin.AdminService;
import com.example.study002.service.Community.CommunityService;
import com.example.study002.service.study.MakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AdminController {

  private final AdminService adminService;
  private final CommunityService communityService;
  private final UserRepository userRepository;
  private final LoginHistoryRepository loginHistoryRepository;
  private final MakeService makeService;

  // 사용자 목록 및 관리자 대시보드 페이지
  @GetMapping("/user/admin")
  public void userList(Model model){
    // 사용자 목록 가져오기
    List<User> userList = adminService.userList();

    // 오늘의 총 방문 횟수 가져오기
    int totalVisitCount = communityService.visitCountForToday();
    model.addAttribute("userList", userList);
    model.addAttribute("totalVisitCount", totalVisitCount);

    // 오늘의 시작부터 끝까지의 범위 내에서 로그인한 이력을 조회
    LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    List<LoginHistory> loginHistories = loginHistoryRepository.findByLoginTimeBetween(startOfDay, endOfDay);

    // 오늘 로그인한 사용자 수
    long loggedUsersCount = loginHistories.size();

    // 생성된 스터디 수 조회
    long makeListCount = makeService.getMakeListCount();

    model.addAttribute("loggedUsersCount", loggedUsersCount);
    model.addAttribute("makeListCount", makeListCount);
  }

  // 사용자 탈퇴 처리
  @GetMapping("/user/admin/userDelete")
  public String userDelete(Long id){
    adminService.remove(id);
    return "redirect:/user/admin";    // 관리자 페이지로 리다이렉트
  }
}
