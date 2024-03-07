package com.example.study002.service.Media;

import com.example.study002.domain.media.MediaImg;
import com.example.study002.repository.media.MediaImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class MediaImgService {

    @Value("${ImgLocation}")
    private String itemImgLocation;
    private final MediaImgRepository mediaImgRepository;
    @Autowired
    private final FileService fileService;

    // 이미지 저장
    public void saveMediaImg(MediaImg mediaImg, MultipartFile mediaImgFile)
            throws Exception{
        String oriImgName = mediaImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                    mediaImgFile.getBytes());
            imgUrl = "/image/photo/" + imgName;
        }


        // 이미지 정보 저장
        mediaImg.updateMediaImg(oriImgName, imgName, imgUrl);
        mediaImgRepository.save(mediaImg);



    }

    // 이미지 URL 가져오기
    public String getURL(Long eid) {
        return mediaImgRepository.findRepUrl(eid);
    }

    // 이미지 수정
    public void updateMediaImg(Long mediaImgId, MultipartFile mediaImgFile)
            throws Exception {
        if (!mediaImgFile.isEmpty()) {
            MediaImg savedMediaImg = mediaImgRepository.findById(mediaImgId)
                    .orElseThrow(EntityNotFoundException:: new);

            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedMediaImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedMediaImg.getImgName());
            }

            String oriImgName = mediaImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, mediaImgFile.getBytes());
            String imgUrl = "/image/photo/" + imgName;
            savedMediaImg.updateMediaImg(oriImgName, imgName, imgUrl);
        }
    }
}