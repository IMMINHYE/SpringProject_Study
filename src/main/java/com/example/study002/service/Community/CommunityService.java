package com.example.study002.service.Community;

import com.example.study002.domain.user.User;
import com.example.study002.dto.community.CommunityDTO;
import com.example.study002.dto.community.CommunityListReplyCountDTO;
import com.example.study002.dto.community.PageRequestDTO;
import com.example.study002.dto.community.PageResponseDTO;

public interface CommunityService {
    // 게시글 등록
    Long register(CommunityDTO communityDTO, User user);

    // 특정 게시글 조회
    CommunityDTO getBoard(Long bno);

    // 게시글 수정
    Long modify(CommunityDTO communityDTO);

    // 게시글 삭제
    void remove(Long bno);

    // 모든 게시글 목록 페이징하여 가져오기
    PageResponseDTO<CommunityListReplyCountDTO> listWidReplyCount(PageRequestDTO pageRequestDTO);

    // 조회수
    public int visitCountForToday();
}
