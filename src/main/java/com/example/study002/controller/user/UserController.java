package com.example.study002.controller.user;

import com.example.study002.config.auth.PrincipalDetails;
import com.example.study002.domain.study.MakeEntity;
import com.example.study002.domain.user.User;
import com.example.study002.repository.user.UserRepository;
import com.example.study002.service.study.MakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final MakeService makeService;

  // 메인 페이지 및 스터디 목록 표시
  @GetMapping({"/", "/index"})
  public String index(@PageableDefault(size = 10) Pageable pageable, Model model) {
    Page<MakeEntity> makelist = makeService.makelist(pageable);
    model.addAttribute("makelist", makelist);

    return "index";
  }

  // 로그인 페이지로 이동
  @GetMapping("/user/login")
  public void login(@RequestParam(value = "error", required = false) String error,
                      @RequestParam(value = "exception", required = false) String exception,
                      Model model) {
    model.addAttribute("error", error);
    model.addAttribute("exception", exception);
  }

  // 사용자 등록 처리
  @PostMapping("/signup")
  public String signup(User user) {
    log.info(user);
    String password = user.getPassword();
    String enPassword = bCryptPasswordEncoder.encode(password);
    user.setPassword(enPassword);
    user.setRole("USER");
    userRepository.save(user);
    return "redirect:/";
  }

  // 사용자 이름 중복 확인
  @GetMapping("/checkDuplicate")
  @ResponseBody
  public boolean checkDuplicate(@RequestParam String username) {
    log.info("checkDuplicate");
    return userRepository.
            existsByUsername(username);
  }

  // 마이페이지로 이동
  @GetMapping("/user/mypage")
  public void mypage(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    User user = principalDetails.getUser();

    model.addAttribute("user", user);
  }

  // 비밀번호 수정 처리
  @PostMapping("/user/editPassword")
  public String editPassword(@RequestParam String newPassword, RedirectAttributes redirectAttributes) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    User user = principalDetails.getUser();

    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
    userRepository.save(user);

    redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 성공적으로 수정되었습니다.");

    return "redirect:/user/mypage";
  }
}
