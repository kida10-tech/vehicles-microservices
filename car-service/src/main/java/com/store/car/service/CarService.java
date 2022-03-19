package com.store.car.service;

import com.store.car.entity.CarEntity;
import com.store.car.repository.CarRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRespository carRespository;

    public List<CarEntity> getAll() {
        return carRespository.findAll();
    }

    public CarEntity getCarById(Long id) {
        return carRespository.findById(id).orElse(null);
    }

    public CarEntity save(CarEntity car) {
        return carRespository.save(car);
    }

    public List<CarEntity> findByUserId(Long id) {
        return carRespository.findByUserId(id);
    }

}
