package com.example.study002.service.Community;

import com.example.study002.dto.community.CommunityReplyDTO;
import com.example.study002.dto.community.PageRequestDTO;
import com.example.study002.dto.community.PageResponseDTO;

public interface CommunityReplyService {
    // 댓글 등록
    Long register(CommunityReplyDTO dto);

    // 댓글 조회
    CommunityReplyDTO read(Long rno);

    // 댓글 수정
    void modify(CommunityReplyDTO replyDTO);

    // 댓글 삭제
    void remove(Long rno);

    // 해당 게시물의 댓글 목록을 페이징하여 가져오기
    PageResponseDTO<CommunityReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
}
