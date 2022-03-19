package com.store.bike.repository;

import com.store.bike.entity.BikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRespository extends JpaRepository<BikeEntity, Long> {

    List<BikeEntity> findByUserId(Long userId);
}
