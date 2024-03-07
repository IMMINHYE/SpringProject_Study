package com.example.study002.domain.community;

import com.example.study002.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

/**
 * Community 엔티티는 커뮤니티 게시글을 나타내는 클래스입니다.
 * BaseEntity를 상속하여 생성일시와 수정일시를 자동으로 관리합니다.
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Community extends BaseEntity{
    // 게시글의 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    // 게시글 제목
    @Column(nullable = false, length = 255)
    private String title;

    // 게시글 내용
    @Lob
    @Column(nullable = false)
    private String content;

    // 게시글 작성자 이름
    @Column(nullable = false, length = 45)
    private String writer;

    // 게시글 조회수 (기본값: 0)
    @ColumnDefault("0")
    private int visitcount;

    // 게시글 작성자를 나타내는 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    /**
     * 게시글의 제목과 내용을 변경하는 메서드
     * @param title 변경할 제목
     * @param content 변경할 내용
     */
    public void change(String title, String content){
        this.title=title;
        this.content=content;
    }

    /**
     * 게시글 조회수를 1 증가시키는 메서드
     */
    public void updateVisitcount(){
        this.visitcount++;
    }
}
