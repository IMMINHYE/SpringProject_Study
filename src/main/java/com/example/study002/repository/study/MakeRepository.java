package com.example.study002.repository.study;

import com.example.study002.domain.study.MakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MakeRepository  extends JpaRepository<MakeEntity, Long> {
    Optional<MakeEntity> findById(Long sid);
}
