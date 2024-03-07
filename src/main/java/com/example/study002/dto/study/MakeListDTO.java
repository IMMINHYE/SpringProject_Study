package com.example.study002.dto.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * MakeListDTO 클래스는 스터디 리스트 조회에 필요한 정보를 전달하기 위한 데이터 전송 객체입니다.
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeListDTO {
    @Builder.Default
    // 페이지 번호, 기본값은 1
    private int page = 1;

    // 페이지당 아이템 수, 기본값은 10
    @Builder.Default
    private int size = 10;

    // 검색의 종류 (t,c, w, tc, tw, twc)
    private String type;

    // 검색 키워드
    private String keyword;

    /**
     * 검색 종류를 배열로 반환합니다.
     *
     * @return 검색 종류 배열
     */
    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split("");
    }

    /**
     * 페이지 요청 정보를 생성하여 반환합니다.
     *
     * @param props 정렬할 속성
     * @return 페이지 요청 정보
     */
    public Pageable getPageable(String...props) {
        return PageRequest.of(this.page -1, this.size,
                Sort.by(props).descending());
    }

    private String link;

    /**
     * 페이지 링크를 반환합니다.
     *
     * @return 페이지 링크
     */
    public String getLink() {

        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

            if(type != null && type.length() > 0){
                builder.append("&type=" + type);
            }

            if(keyword != null){
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            link = builder.toString();
        }

        return link;
    }
}