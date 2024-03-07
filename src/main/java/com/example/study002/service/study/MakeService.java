package com.example.study002.service.study;

import com.example.study002.domain.user.User;
import com.example.study002.dto.study.MakeDTO;
import com.example.study002.domain.study.MakeEntity;
import com.example.study002.repository.study.MakeRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class MakeService {
    @Autowired
    private MakeRepository makeRepository;
    @Autowired
    private ModelMapper modelMapper;

    // 모든 미디어 목록 조회
    public List<MakeEntity> getList() {
        return makeRepository.findAll();
    }

    // 페이지네이션 적용하여 모든 미디어 목록 조회
    public Page<MakeEntity> makelist(Pageable pageable) {

        return makeRepository.findAll(pageable);
    }

    // 미디어 등록
    public Long saveMake(MakeDTO makeDTO, User user) {
        MakeEntity makeEntity = new MakeEntity(makeDTO.getStudyName(), makeDTO.getPhoneNumber(), makeDTO.getEmail(), makeDTO.getPost(), makeDTO.getPersons(), makeDTO.getProgressMethod(), makeDTO.getCloseDate(), makeDTO.getIntro());
        makeEntity.setUser(user);
        Long sid = makeRepository.save(makeEntity).getSid();
        return sid;
    }


    // 특정 미디어 조회
    public MakeDTO getmake(Long sid) {
        MakeEntity makeEntity = makeRepository.findById(sid).orElseThrow();
        MakeDTO makeDTO = MakeDTO.builder()
                .sid(makeEntity.getSid())
                .closeDate(makeEntity.getCloseDate())
                .email(makeEntity.getEmail())
                .intro(makeEntity.getIntro())
                .phoneNumber(makeEntity.getPhoneNumber())
                .progressMethod(makeEntity.getProgressMethod())
                .studyName(makeEntity.getStudyName())
                .post(makeEntity.getPost())
                .persons(makeEntity.getPersons())
                .user_id(makeEntity.getUser().getId())
                .build();
        return makeDTO;
    }

    // 미디어 삭제
    public void remove(Long sid){
        makeRepository.deleteById(sid);
    }

    // 미디어 수정
    public void modify(MakeDTO updatedMakeDTO) {
        MakeEntity existingMakeEntity = makeRepository.findById(updatedMakeDTO.getSid())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 데이터가 존재하지 않습니다."));
        existingMakeEntity.setCloseDate(updatedMakeDTO.getCloseDate());
        existingMakeEntity.setPersons(updatedMakeDTO.getPersons());
        makeRepository.save(existingMakeEntity);
    }

    // 미디어 수
    public long getMakeListCount() {
        return makeRepository.count();
    }
}





