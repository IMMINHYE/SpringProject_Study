package com.example.study002.service.Community;

import com.example.study002.domain.community.Community;
import com.example.study002.domain.user.User;
import com.example.study002.dto.community.CommunityDTO;
import com.example.study002.dto.community.CommunityListReplyCountDTO;
import com.example.study002.dto.community.PageRequestDTO;
import com.example.study002.dto.community.PageResponseDTO;
import com.example.study002.repository.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final ModelMapper modelMapper;

    // 게시글 등록
    @Override
    public Long register(CommunityDTO communityDTO, User user) {
        Community community = modelMapper.map(communityDTO, Community.class);
        community.setUser(user);
        community.setWriter(user.getName());
        Long bno = communityRepository.save(community).getBno();
        return bno;
    }

    // 특정 게시글 조회
    @Override
    public CommunityDTO getBoard(Long bno) {
        Optional<Community> result = communityRepository.findById(bno);
        Community community = result.get();
        community.updateVisitcount();
        communityRepository.save(community);
        CommunityDTO communityDTO = CommunityDTO.builder()
                .bno(community.getBno())
                .title(community.getTitle())
                .writer(community.getWriter())
                .content(community.getContent())
                .visitcount(community.getVisitcount())
                .regDate(community.getRegDate())
                .user_id(community.getUser().getId())
                .build();
        return communityDTO;
    }

    // 게시글 수정
    @Override
    public Long modify(CommunityDTO communityDTO) {
        Optional<Community> result = communityRepository.findById(communityDTO.getBno());
        Community community = result.orElseThrow();
        community.change(communityDTO.getTitle(), communityDTO.getContent());
        Long bno = communityRepository.save(community).getBno();
        return bno;
    }

    // 게시글 삭제
    @Override
    public void remove(Long bno) {
        communityRepository.deleteById(bno);
    }

    // 모든 게시글 페이징하여 가져오기
    @Override
    public PageResponseDTO<CommunityListReplyCountDTO> listWidReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<CommunityListReplyCountDTO> result =
                communityRepository.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<CommunityListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int) result.getTotalElements())
                .build();
    }

    // 조회수
    @Override
    public int visitCountForToday() {
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return communityRepository.visitCountForToday(todayStart, todayEnd);
    }
}
