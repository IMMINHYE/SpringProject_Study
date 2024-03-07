package com.example.study002.service.Community;

import com.example.study002.domain.community.Community;
import com.example.study002.domain.community.CommunityReply;
import com.example.study002.dto.community.CommunityReplyDTO;
import com.example.study002.dto.community.PageRequestDTO;
import com.example.study002.dto.community.PageResponseDTO;
import com.example.study002.repository.community.CommunityReplyRepository;
import com.example.study002.repository.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommunityReplyServiceImpl implements CommunityReplyService{

    private final CommunityReplyRepository communityReplyRepository;
    private final ModelMapper modelMapper;
    private final CommunityRepository communityRepository;

    // 댓글 등록
    @Override
    public Long register(CommunityReplyDTO dto) {
        CommunityReply communityReply = modelMapper.map(dto, CommunityReply.class);
        Community community = Community.builder().bno(dto.getBno()).build();
        communityReply.setCommunity(community);
        Long rno = communityReplyRepository.save(communityReply).getRno();
        return rno;
    }

    // 댓글 조회
    @Override
    public CommunityReplyDTO read(Long rno) {
        Optional<CommunityReply> result = communityReplyRepository.findById(rno);
        CommunityReply communityReply = result.get();
        CommunityReplyDTO dto = modelMapper.map(communityReply, CommunityReplyDTO.class);
        return dto;
    }

    // 댓글 수정
    @Override
    public void modify(CommunityReplyDTO replyDTO) {
        CommunityReply communityReply = communityReplyRepository.findById(replyDTO.getRno()).get();
        communityReply.changeText(replyDTO.getReplytext());
        communityReplyRepository.save(communityReply);
    }

    // 댓글 삭제
    @Override
    public void remove(Long rno) {
        communityReplyRepository.deleteById(rno);
    }

    // 해당 게시물의 댓글 목록을 페이징하여 가져오기
    @Override
    public PageResponseDTO<CommunityReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("rno").descending()
        );

        Page<CommunityReply> result = communityReplyRepository.listOfBoard(bno, pageable);
        List<CommunityReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, CommunityReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<CommunityReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}