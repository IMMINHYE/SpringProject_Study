package com.example.study002.controller.study;

import com.example.study002.config.auth.PrincipalDetails;
import com.example.study002.dto.study.MakeDTO;
import com.example.study002.domain.study.MakeEntity;
import com.example.study002.service.study.MakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/study")
public class MakeController {

    private final MakeService makeService;

    // 스터디 생성 목록 페이지
    @GetMapping("/makeList")
    public void makelist(@PageableDefault(size = 1) Pageable pageable, Model model) {
        Page<MakeEntity> makelist = makeService.makelist(pageable);
        Map<MakeEntity, String> totalList = new HashMap<>();
        model.addAttribute("makeAll", totalList);
        model.addAttribute("makelist", makelist);
    }

    // 스터디 생성 처리
    @PostMapping("/makeRegister")
    public String makeNew(@Valid @ModelAttribute MakeDTO makeDTO, Model model, RedirectAttributes regs, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info(makeDTO);
        Long sid = makeService.saveMake(makeDTO, principalDetails.getUser());
        log.info(sid);
        regs.addFlashAttribute("msg","생성 성공");
        return "redirect:/study/studylist";
    }

    // 스터디 생성 폼 페이지
    @GetMapping("/make")
    public void makeForm() {

    }

    // 스터디 정보 수정 처리
    @PostMapping("/modify")
    public String modify(@Valid MakeDTO makeDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        log.info("board modify post......." + makeDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", makeDTO.getSid());
            return "redirect:/study/modify";
        }
        makeService.modify(makeDTO);
        return "redirect:/study/view?sid="+makeDTO.getSid();
    }

    // 스터디 목록 페이지
    @GetMapping("/studylist")
    public void list(Model model, @PageableDefault(size = 10, sort = "sid",
    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MakeEntity> pageMake=makeService.makelist(pageable);
        List<MakeEntity> makeEntities=pageMake.getContent();
        model.addAttribute("makeList",makeEntities);
        model.addAttribute("pageMake",makeService.makelist(pageable));
    }

    // 스터디 상세 정보 페이지 및 수정 페이지
    @GetMapping({"/view","/modify"})
    public void view(@RequestParam("sid") Long sid, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("페이지를 이동합니다.");
        MakeDTO makeDTO = makeService.getmake(sid);
        model.addAttribute("makedto",makeDTO);
        model.addAttribute("login", principalDetails.getUser());
    }

    // 스터디 삭제 처리
    @GetMapping("/remove")
    public String remove(Long sid){
        makeService.remove(sid);
        return "redirect:/study/studylist";
  }
}