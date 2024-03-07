package com.example.study002.domain.media;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * MediaImg 엔티티는 자료실 미디어 정보와 관련된 이미지를 나타냅니다.
 */

@Entity
@Table(name = "mediaimg")
@Getter
@Setter
public class MediaImg {
    // 이미지 고유 식별자
    @Id
    @Column(name = "eiid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eiid;

    // 파일명
    private String imgName;

    // 원본 이미지 파일명
    private String oriImgName;

    // 경로
    private String imgUrl;

    // 대표 이미지 여부
    private String repImgYn;

    // 미디어 정보와의 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eid")
    private MediaInfo mediainfo;

    /**
     * 이미지 정보를 업데이트하는 메서드
     * @param oriImgName 업데이트할 원본 이미지 파일명
     * @param imgName 업데이트할 이미지 파일명
     * @param imgUrl 업데이트할 이미지 경로
     */
    public void updateMediaImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}