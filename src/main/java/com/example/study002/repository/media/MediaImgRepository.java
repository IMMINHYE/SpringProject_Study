package com.example.study002.repository.media;

import com.example.study002.domain.media.MediaImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MediaImgRepository extends JpaRepository<MediaImg, Long> {

    // 미디어 이미지 목록 조회
    List<MediaImg> findAllByMediainfo_EidOrderByEiidAsc(Long eid);

    // 특정 MediaInfo의 ID에 해당하는 대표 이미지 URL을 가져오기
    @Query(value = "select mi.img_url from mediaimg mi where mi.eid = :eid and mi.rep_img_yn = 'Y'", nativeQuery = true)
    String findRepUrl(Long eid);
}
