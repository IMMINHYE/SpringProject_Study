package com.example.study002.repository.search;

import com.example.study002.domain.community.Community;
import com.example.study002.dto.community.CommunityListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunitySearch {
    // 모든 Community 게시글 조회
    Page<Community> search1(Pageable pageable);

    // 지정된 유형 및 키워드에 따라 Community를 검색
    Page<Community> searchAll(String[] types, String keyword, Pageable pageable);

    // 댓글 수를 포함하여 지정된 유형 및 키워드에 따라 Community를 검색
    Page<CommunityListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);
}
