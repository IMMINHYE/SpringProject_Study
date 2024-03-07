package com.example.study002.dto.study;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.log4j.Log4j2;

/**
 * MakeDTO 클래스는 스터디 생성에 필요한 정보를 전달하기 위한 데이터 전송 객체입니다.
 */

@Getter
@Setter
@Log4j2
@ToString
@Builder
public class MakeDTO {

    // 스터디 ID
    private Long sid;

    // 스터디 이름
    @Size(min = 3, max = 100)
    private String studyName;

    // 전화번호
    @NotEmpty
    private String phoneNumber;

    // 이메일
    @NotEmpty
    private String email;

    // 소개글
    private String intro;

    // 주소
    private String post;

    // 인원수
    @NotEmpty
    private String persons;

    // 진행 방식
    private String progressMethod;

    // 마감일
    private String closeDate;

    // 사용자 ID
    private Long user_id;
}