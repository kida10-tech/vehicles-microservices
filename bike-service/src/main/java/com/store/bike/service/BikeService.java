package com.store.bike.service;

import com.store.bike.entity.BikeEntity;
import com.store.bike.repository.BikeRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BikeService {

    private final BikeRespository bikeRespository;

    public List<BikeEntity> getAll() {
        return bikeRespository.findAll();
    }

    public BikeEntity getBikeById(Long id) {
        return bikeRespository.findById(id).orElse(null);
    }

    public BikeEntity save(BikeEntity bike) {
        return bikeRespository.save(bike);
    }

    public List<BikeEntity> findByUserId(Long id) {
        return bikeRespository.findByUserId(id);
    }

}
