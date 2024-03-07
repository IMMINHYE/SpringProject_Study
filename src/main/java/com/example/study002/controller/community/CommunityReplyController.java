package com.example.study002.controller.community;

import com.example.study002.dto.community.CommunityReplyDTO;
import com.example.study002.dto.community.PageRequestDTO;
import com.example.study002.dto.community.PageResponseDTO;
import com.example.study002.service.Community.CommunityReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class CommunityReplyController {
    private final CommunityReplyService communityReplyService;

    // 댓글 등록 요청 처리하는 핸들러
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody CommunityReplyDTO communityReplyDTO,
                                      BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        Map<String, Long> resultMap = new HashMap<>();
        Long rno = communityReplyService.register(communityReplyDTO);
        resultMap.put("rno", rno);
        return resultMap;
    }

    // 특정 게시글의 댓글 목록 조회 요청 처리하는 핸들러
    @GetMapping("/list/{bno}")
    public PageResponseDTO<CommunityReplyDTO> getList(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<CommunityReplyDTO> responseDTO = communityReplyService.getListOfBoard(bno, pageRequestDTO);
        return responseDTO;
    }

    // 특정 댓글 조회 요청 처리하는 핸들러
    @GetMapping("/{rno}")
    public CommunityReplyDTO getReplyDTO(@PathVariable("rno") Long rno) {
        CommunityReplyDTO communityReplyDTO = communityReplyService.read(rno);
        return communityReplyDTO;
    }

    // 댓글 수정 요청 처리하는 핸들러
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") Long rno,
                                    @RequestBody CommunityReplyDTO communityReplyDTO) {
        communityReplyDTO.setRno(rno);
        communityReplyService.modify(communityReplyDTO);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("댓글 수정", rno);
        return resultMap;
    }

    // 댓글 삭제 요청 처리하는 핸들러
    @DeleteMapping("/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno) {
        communityReplyService.remove(rno);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("댓글 삭제", rno);
        return resultMap;
    }
}
