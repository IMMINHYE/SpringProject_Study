package com.example.study002.dto.media;

import com.example.study002.domain.media.MediaImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

/**
 * MediaImgDTO 클래스는 미디어 이미지 정보를 전달하기 위한 데이터 전송 객체입니다.
 */

@Getter
@Setter
public class MediaImgDTO {

    // 미디어 이미지 ID
    private Long eiid;

    // 파일명
    private String imgName;

    // 원본 이미지 파일명
    private String oriImgName;

    // 경로
    private String imgUrl;

    // 대표 이미지 여부
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    /**
     * 주어진 MediaImg 엔티티를 기반으로 MediaImgDTO 객체를 생성하여 반환합니다.
     *
     * @param mediaImg MediaImg 엔티티
     * @return 생성된 MediaImgDTO 객체
     */
    public static MediaImgDTO of(MediaImg mediaImg){
        return modelMapper.map(mediaImg, MediaImgDTO.class);
    }

}


