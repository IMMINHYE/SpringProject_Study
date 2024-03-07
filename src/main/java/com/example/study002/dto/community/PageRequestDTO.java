package com.example.study002.dto.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * PageRequestDTO 클래스는 페이지 요청 정보를 전달하기 위한 데이터 전송 객체입니다.
 */

@Log4j2
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    // 페이지 번호 (기본값: 1)
    @Builder.Default
    private int page = 1;

    // 한 페이지당 항목 수 (기본값: 10)
    @Builder.Default
    private int size = 10;

    // 검색 종류 (t, c, w, tc, tw, twc)
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
     * 페이지 요청 정보를 기반으로 Pageable 객체를 생성하여 반환합니다.
     *
     * @param props 정렬 기준 필드 배열
     * @return Pageable 객체
     */
    public Pageable getPageable(String...props) {
        return PageRequest.of(this.page -1, this.size,
                Sort.by(props).descending());
    }

    // 페이지 링크
    private String link;

    /**
     * 페이지 링크를 생성하여 반환합니다.
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
        log.info(link);

        return link;
    }
}