package com.example.study002.domain.community;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BaseEntity는 모든 엔티티의 공통 속성을 포함하는 추상 클래스입니다.
 * 모든 엔티티에 대해 생성일시와 수정일시를 관리합니다.
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    // 생성일시를 나타내는 필드로, 엔티티가 저장될 때 자동으로 현재 시간으로 설정
    @CreationTimestamp
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    // 수정일시를 나타내는 필드로, 엔티티가 수정될 때 자동으로 현재 시간으로 설정
    @UpdateTimestamp
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
