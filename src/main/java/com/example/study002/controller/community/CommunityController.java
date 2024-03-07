package com.example.study002.controller.community;

import com.example.study002.config.auth.PrincipalDetails;
import com.example.study002.dto.community.CommunityDTO;
import com.example.study002.dto.community.CommunityListReplyCountDTO;
import com.example.study002.dto.community.PageRequestDTO;
import com.example.study002.dto.community.PageResponseDTO;
import com.example.study002.service.Community.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    // 게시글 등록 페이지로 이동하는 핸들러
    @GetMapping("/register")
    public void register() {

    }

    // 게시글 등록 요청 처리하는 핸들러
    @PostMapping("/register")
    public String register(CommunityDTO communityDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long bno = communityService.register(communityDTO, principalDetails.getUser());
        if (bno != null) {
            log.info(bno);
            return "redirect:/community/list";
        }
        return "redirect:/community/register";
    }

    // 게시글 상세보기 및 수정 페이지로 이동하는 핸들러
    @GetMapping({"/view", "modify"})
    public void view(Long bno, PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("dto", communityService.getBoard(bno));
        model.addAttribute("login", principalDetails.getUser());
    }

    // 게시글 수정 요청 처리하는 핸들러
    @PostMapping("/modify")
    public String modify(CommunityDTO communityDTO) {
        Long bno = communityService.modify(communityDTO);
        if (bno != null) {
            return "redirect:/community/view?bno=" + bno;
        }
        return "redirect:/community/modify?bno=" + bno;
    }

    // 게시글 삭제 요청 처리하는 핸들러
    @GetMapping("/remove")
    public String remove(Long bno){
        communityService.remove(bno);
        return "redirect:/community/list";
    }

    // 게시글 목록 페이지로 이동하는 핸들러
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<CommunityListReplyCountDTO> responseDTO = communityService.listWidReplyCount(pageRequestDTO);
        model.addAttribute("responseDTO", responseDTO);
    }

}
