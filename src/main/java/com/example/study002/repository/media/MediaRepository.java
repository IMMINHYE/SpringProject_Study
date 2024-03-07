package com.example.study002.repository.media;

import com.example.study002.domain.media.MediaInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaInfo, Long> {

    // 특정 MediaInfo의 ID에 해당하는 미디어 정보 가져오기
    MediaInfo findByEid(Long eid);
}
