package com.example.study002.controller.media;

import com.example.study002.dto.media.MediaFormDTO;
import com.example.study002.domain.media.MediaInfo;
import com.example.study002.service.Media.MediaImgService;
import com.example.study002.service.Media.MediaInfoService;
import com.example.study002.service.Media.MediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/media")
@Log4j2
@RequiredArgsConstructor
public class MediaInfoController {

    private final MediaService mediaService;

    @Autowired
    MediaInfoService mediaInfoService;

    @Autowired
    MediaImgService mediaImgService;

    // 미디어 목록 페이지
    @GetMapping("/medialist")
    public void medialist(@PageableDefault(size = 8) Pageable pageable, Model model) {
        // 페이지네이션 처리된 미디어 목록을 가져옴
        Page<MediaInfo> medialist = mediaInfoService.medialist(pageable);
        List<MediaInfo> ml = medialist.getContent();

        List<MediaInfo> urls = new ArrayList<>();

        Map<MediaInfo, String> totalList = new HashMap<>();
        for(MediaInfo m : ml) {
            String fileURL = mediaImgService.getURL(m.getEid());
            String elink=m.getElink();
            log.info("@@@@@@@@@@@@@@@"+elink);
            m.setElink(elink);

            totalList.put(m, fileURL);

            log.info(m.getElink());
        }

        model.addAttribute("mediaAll", totalList);

        model.addAttribute("urls", urls);
        model.addAttribute("medialist", medialist);

    }

    // 미디어 등록 페이지
    @GetMapping("/mediaRegister")
    public String mediaRegister(Model model) {
        model.addAttribute("mediaFormDTO", new MediaFormDTO());
        return "/media/mediaRegister";
    }

    // 미디어 등록 처리
    @PostMapping("/mediaRegister")
    public String MediaNew(@ModelAttribute("mediaFormDTO") @Valid MediaFormDTO MediaFormDTO,
                           BindingResult bindingResult, Model model, @RequestParam("mediaImgFile") List<MultipartFile>
                                   mediaImgFileList) {
        if (bindingResult.hasErrors()) {
            return "/media/mediaRegister";
        }

        if (mediaImgFileList.get(0).isEmpty() && MediaFormDTO.getEid() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "/media/mediaRegister";
        }

        try {
            mediaService.saveMedia(MediaFormDTO, mediaImgFileList);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "/media/mediaRegister";
        }

        return "redirect:/media/medialist";
    }

    // 미디어 수정 페이지
    @GetMapping("/mediaRegister/{eid}")
    public String Modify(@PathVariable("eid") Long eid, Model model) {
        try {
            MediaFormDTO mediaFormDTO = mediaService.getModify(eid);
            model.addAttribute("mediaFormDTO", mediaFormDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("mediaFormDTO", new MediaFormDTO());
            return "/media/mediaRegister";
        }

        return "/media/mediaRegister";
    }

    // 미디어 수정 처리
    @PostMapping("/mediaRegister/{eid}")
    public String mediaUpdate(@Valid MediaFormDTO mediaFormDTO, BindingResult bindingResult,
                              @RequestParam("mediaImgFile") List<MultipartFile>
                                      mediaImgFileList, Model model) {
        if (bindingResult.hasErrors()) {
            return "/media/mediaRegister";
        }

        if (mediaImgFileList.get(0).isEmpty() && mediaFormDTO.getEid() == null) {
            model.addAttribute("errorMessage", "첫번째 사진 이미지는 필수 입력 값 입니다.");
            return "/media/mediaRegister";
        }

        try {
            mediaService.updateMedia(mediaFormDTO, mediaImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사진 수정 중 에러가 발생하였습니다.");
            return "/media/mediaRegister";
        }

        return "redirect:/media/medialist";
    }

    @GetMapping("/modify")
    public String Modify(Model model, @RequestParam("eid") Long eid) {
        MediaFormDTO mediaFormDTO = mediaService.getModify(eid);
        model.addAttribute("media", mediaFormDTO);

        return "/media/Modify";
    }

    // 미디어 삭제 처리
    @PostMapping("/del")
    public String deleteMedia(Long eid) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~" + eid);
        mediaService.deleteMedia(eid);
        return "redirect:/media/medialist";
    }


}


