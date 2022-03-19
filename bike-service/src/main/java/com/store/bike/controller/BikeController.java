package com.store.bike.controller;

import com.store.bike.entity.BikeEntity;
import com.store.bike.service.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bike")
public class BikeController {

    private final BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<BikeEntity>> getAll() {
        List<BikeEntity> bikes = bikeService.getAll();
        if(bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bikes);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeEntity> getById(@PathVariable Long id) {
        BikeEntity bike = bikeService.getBikeById(id);
        if(bike == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(bike);
        }
    }

    @PostMapping
    public ResponseEntity<BikeEntity> save(@RequestBody BikeEntity bike) {
        BikeEntity newBike = bikeService.save(bike);
        return ResponseEntity.ok(newBike);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<BikeEntity>> findByUserId(@PathVariable("userId") Long userId) {
        List<BikeEntity> bikes = bikeService.findByUserId(userId);
        return ResponseEntity.ok(bikes);

    }
}
