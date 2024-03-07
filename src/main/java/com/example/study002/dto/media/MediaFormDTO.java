package com.example.study002.dto.media;

import com.example.study002.domain.media.MediaInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;

/**
 * MediaFormDTO 클래스는 미디어 정보를 전달하기 위한 데이터 전송 객체입니다.
 */

@Log4j2
@Getter
@Setter
@ToString
public class MediaFormDTO {

    private Long eid;

    @NotBlank(message = "제목 필수 입력")
    private String etitle;

    @NotBlank(message = "url 필수 입력")
    private String elink;

    private List<MediaImgDTO> mediaImgDTOList = new ArrayList<>();

    private List<Long> mediaImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public MediaInfo createMediaInfo(){

        return modelMapper.map(this,MediaInfo.class);
    }

    /**
     * 주어진 MediaInfo 엔티티를 기반으로 MediaFormDTO 객체를 생성하여 반환합니다.
     *
     * @param mediainfo MediaInfo 엔티티
     * @return 생성된 MediaFormDTO 객체
     */
    public static MediaFormDTO of(MediaInfo mediainfo){
        log.info("dto of:" + mediainfo);
        log.info("mepped mediaformdto:"+ modelMapper.map(mediainfo,MediaFormDTO.class));
        return modelMapper.map(mediainfo,MediaFormDTO.class);
    }
}
