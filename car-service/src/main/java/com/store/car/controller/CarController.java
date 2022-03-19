package com.store.car.controller;

import com.store.car.entity.CarEntity;
import com.store.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarEntity>> getAll() {
        List<CarEntity> cars = carService.getAll();
        if(cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(cars);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarEntity> getById(@PathVariable Long id) {
        CarEntity car = carService.getCarById(id);
        if(car == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(car);
        }
    }

    @PostMapping
    public ResponseEntity<CarEntity> save(@RequestBody CarEntity car) {
        CarEntity newCar = carService.save(car);
        return ResponseEntity.ok(newCar);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<CarEntity>> findByUserId(@PathVariable("userId") Long userId) {
        List<CarEntity> cars = carService.findByUserId(userId);
        return ResponseEntity.ok(cars);

    }
}
