package com.example.study002.domain.community;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommunityReply 엔티티는 커뮤니티 게시글에 대한 댓글을 나타내는 클래스입니다.
 * BaseEntity를 상속하여 생성일시와 수정일시를 자동으로 관리합니다.
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityReply extends BaseEntity{
    // 댓글의 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    // 댓글이 속한 커뮤니티 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    private Community community;

    // 댓글 내용
    private String replytext;

    // 댓글 작성자
    private String replyer;

    /**
     * 댓글 내용을 변경하는 메서드
     * @param text 변경할 댓글 내용
     */
    public void changeText(String text) {
        this.replytext = text;
    }
}
