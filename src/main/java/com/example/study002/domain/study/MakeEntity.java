package com.example.study002.domain.study;

import com.example.study002.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * MakeEntity 엔티티는 스터디 생성 정보를 나타냅니다.
 */

@Getter
@ToString
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "studymake")
public class MakeEntity {
    // 스터디 생성 정보의 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    // 스터디명
    @Column(length = 500, nullable = false)
    private String studyName;

    // 전화번호
    @Column(length = 500, nullable = false)
    private String phoneNumber;

    // 이메일
    @Column(length = 500, nullable = false)
    private String email;

    // 주소
    @Column(length = 500, nullable = false)
    private String post;

    // 모집 인원
    @Column(length = 500, nullable = false)
    private String persons;

    // 진행 방법
    @Column(length = 500, nullable = false)
    private String progressMethod;

    // 마감일
    @Column(length = 500, nullable = false)
    private String closeDate;

    // 스터디 소개
    @Column(length = 500, nullable = false)
    private String intro;

    // 스터디를 생성한 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    public MakeEntity(String studyName, String phoneNumber, String email, String post, String persons, String progressMethod, String closeDate, String intro) {
        this.studyName = studyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.post = post;
        this.persons = persons;
        this.progressMethod = progressMethod;
        this.closeDate = closeDate;
        this.intro = intro;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public void setProgressMethod(String progressMethod) {
        this.progressMethod = progressMethod;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }
}
