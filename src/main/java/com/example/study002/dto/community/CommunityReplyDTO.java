package com.example.study002.dto.community;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * CommunityReplyDTO 클래스는 커뮤니티 게시글에 대한 댓글 정보를 전송하기 위한 데이터 전송 객체입니다.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityReplyDTO {
    // 댓글의 고유 식별자
    private Long rno;

    // 댓글의 고유 식별자
    private Long bno;

    // 댓글 내용
    @NotEmpty
    private String replytext;

    // 댓글 작성자
    @NotEmpty
    private String replyer;

    // 댓글 작성일시
    private LocalDateTime regDate;
}
