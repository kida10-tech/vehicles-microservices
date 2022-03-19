package com.store.car.repository;

import com.store.car.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRespository extends JpaRepository<CarEntity, Long> {

    List<CarEntity> findByUserId(Long userId);
}
