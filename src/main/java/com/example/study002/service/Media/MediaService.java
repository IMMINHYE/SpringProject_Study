package com.example.study002.service.Media;

import com.example.study002.dto.media.MediaFormDTO;
import com.example.study002.dto.media.MediaImgDTO;
import com.example.study002.domain.media.MediaImg;
import com.example.study002.domain.media.MediaInfo;
import com.example.study002.repository.media.MediaImgRepository;
import com.example.study002.repository.media.MediaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MediaService {

    private final MediaImgRepository mediaImgRepository;
    private final MediaImgService mediaImgService;
    private final MediaRepository mediaRepository;

    public Long saveMedia(MediaFormDTO mediaFormDTO,
                          List<MultipartFile> mediaImgFileList) throws Exception {
        // 정보 등록
        MediaInfo mediainfo = mediaFormDTO.createMediaInfo();
        mediaRepository.save(mediainfo);

        // 이미지 등록
        for (int i = 0; i < mediaImgFileList.size(); i++) {
            if(mediaImgFileList.get(i).getOriginalFilename() !=null){
                MediaImg mediaImg = new MediaImg();
                mediaImg.setMediainfo(mediainfo);
                if (i == 0)
                    mediaImg.setRepImgYn("Y");
                else
                    mediaImg.setRepImgYn("N");
                mediaImgService.saveMediaImg(mediaImg, mediaImgFileList.get(i));
            }
        }
        return mediainfo.getEid();
    }

    @Transactional(readOnly = true)
    public MediaFormDTO getModify(Long eid) {

        List<MediaImg> mediaImgList =
                mediaImgRepository.findAllByMediainfo_EidOrderByEiidAsc(eid);
        List<MediaImgDTO> mediaImgDTOList = new ArrayList<>();
        for (MediaImg mediaImg : mediaImgList) {
            MediaImgDTO mediaImgDTO = MediaImgDTO.of(mediaImg);
            mediaImgDTOList.add(mediaImgDTO);
        }
        MediaInfo media_info = mediaRepository.findById(eid).orElseThrow(EntityNotFoundException::new);
        MediaFormDTO mediaFormDTO = MediaFormDTO.of(media_info);

        mediaFormDTO.setMediaImgDTOList(mediaImgDTOList);
        return mediaFormDTO;
    }

    // 미디어 정보 수정
    public Long updateMedia(MediaFormDTO mediaFormDTO, List<MultipartFile> mediaImgFileList) throws Exception {

        // 정보 수정
        MediaInfo mediaInfo = mediaRepository.findById(mediaFormDTO.getEid())
                .orElseThrow(EntityNotFoundException::new);
        mediaInfo.updateMedia(mediaFormDTO);
        List<Long> mediaImgIds = mediaFormDTO.getMediaImgIds();

        // 이미지 수정
        for (int i = 0; i < mediaImgFileList.size(); i++) {
            mediaImgService.updateMediaImg(mediaImgIds.get(i), mediaImgFileList.get(i));
        }

        return mediaInfo.getEid();
    }

    // 미디어 삭제
    public void deleteMedia(Long eid) {
        //삭제
        if (mediaRepository.findById(eid).orElse(null) != null) {
            mediaRepository.deleteById(eid);
        }
    }
}