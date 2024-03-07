package com.example.study002.dto.community;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * CommunityListReplyCountDTO 클래스는 커뮤니티 게시글 목록과 해당 게시글에 대한 댓글 수를 저장하는 데이터 전송 객체입니다.
 */

@Data
public class CommunityListReplyCountDTO {
    // 게시글의 고유 식별자
    private Long bno;

    // 게시글 제목
    private String title;

    // 게시글 작성자
    private String writer;

    // 게시글 조회수
    private int visitcount;

    // 게시글 작성일시
    private LocalDateTime regDate;

    // 게시글 작성일시
    private Long replyCount;
}
