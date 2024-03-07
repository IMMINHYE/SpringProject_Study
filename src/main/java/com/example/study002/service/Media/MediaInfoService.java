package com.example.study002.service.Media;

import com.example.study002.domain.media.MediaInfo;
import com.example.study002.repository.media.MediaImgRepository;
import com.example.study002.repository.media.MediaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Log4j2
public class MediaInfoService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaImgRepository mediaImgRepository;

    // 미디어 정보 목록 조회
    public List<MediaInfo> getList(){

        return mediaRepository.findAll();
    }

    // 페이지네이션을 적용하여 미디어 정보 목록 조회
    public Page<MediaInfo> medialist(Pageable pageable) {

        return mediaRepository.findAll(pageable);
    }
}