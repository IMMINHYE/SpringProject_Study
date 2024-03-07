package com.example.study002.repository.community;

import com.example.study002.domain.community.CommunityReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommunityReplyRepository extends JpaRepository<CommunityReply, Long> {
    // 특정 게시글의 댓글 목록 조회
    @Query("select r from CommunityReply r where r.community.bno=:bno")
    Page<CommunityReply> listOfBoard(Long bno, Pageable pageable);
}
