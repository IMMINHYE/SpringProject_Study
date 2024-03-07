package com.example.study002.dto.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * CommunityDTO 클래스는 Community 엔티티의 데이터 전송 객체입니다.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDTO {
    // 게시글의 고유 식별자
    private Long bno;

    // 게시글 제목
    private String title;

    // 게시글 내용
    private String content;

    // 게시글 작성자
    private String writer;

    // 게시글 조회수
    private int visitcount;

    // 게시글 작성일시
    private LocalDateTime regDate;

    // 게시글 작성자의 고유 식별자
    private Long user_id;
}
