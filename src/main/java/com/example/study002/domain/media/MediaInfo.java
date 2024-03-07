package com.example.study002.domain.media;

import com.example.study002.dto.media.MediaFormDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * MediaInfo 엔티티는 미디어 정보를 나타냅니다.
 */

@Entity
@Table(name = "mediainfo")
@Getter
@Setter
public class MediaInfo {
    // 미디어 정보의 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eid;

    // 미디어 제목
    private String etitle;

    // 동영상 주소(url)
    private String elink;

    // 미디어 정보에 속하는 이미지들
    @OneToMany(mappedBy = "mediainfo", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    List<MediaImg> imgs = new ArrayList<>();


    /**
     * 미디어 정보를 업데이트하는 메서드
     * @param mediaFormDTO 업데이트할 미디어 정보 DTO
     */
    public void updateMedia(MediaFormDTO mediaFormDTO) {
        this.etitle = mediaFormDTO.getEtitle();
        this.elink =  mediaFormDTO.getElink();
    }
}
