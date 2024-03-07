package com.example.study002.repository.community;

import com.example.study002.domain.community.Community;
import com.example.study002.repository.search.CommunitySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunitySearch {

    // 게시글 목록 조회
    Page<Community> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

    // 특정 키워드를 제목에 포함하는 게시글 목록을 조회
    @Query("select b from Community b where b.title like concat('%', :keyword,'%') order by b.bno desc ")
    Page<Community> findByKeyword(String keyword,Pageable pageable);

    // 방문자 수 조회
    @Query("SELECT COALESCE(SUM(c.visitcount), 0) FROM Community c WHERE c.regDate BETWEEN :start AND :end")
    int visitCountForToday(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
