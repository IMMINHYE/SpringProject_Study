package com.example.study002.dto.community;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

/**
 * PageResponseDTO 클래스는 페이지 응답 정보를 전달하기 위한 데이터 전송 객체입니다.
 *
 * @param <E> DTO의 타입
 */

@Getter
@ToString
public class PageResponseDTO<E> {
    // 현재 페이지 번호
    private int page;

    // 한 페이지당 항목 수
    private int size;

    // 전체 항목 수
    private int total;

    //시작 페이지 번호
    private int start;

    //끝 페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;

    //다음 페이지의 존재 여부
    private boolean next;

    // DTO 리스트
    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){

        if(total <= 0){
            return;
        }

        // 현재 페이지 정보 설정
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        // 전체 항목 수 설정
        this.total = total;
        this.dtoList = dtoList;

        // 시작 페이지와 끝 페이지 설정
        this.end =   (int)(Math.ceil(this.page / 10.0 )) *  10;
        this.start = this.end - 9;

        int last =  (int)(Math.ceil((total/(double)size)));
        this.end =  end > last ? last: end;

        // 이전 페이지와 다음 페이지 설정
        this.prev = this.start > 1;
        this.next =  total > this.end * this.size;
    }
}
