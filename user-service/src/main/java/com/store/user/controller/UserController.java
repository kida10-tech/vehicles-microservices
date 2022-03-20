package com.store.user.controller;

import com.store.user.entity.UserEntity;
import com.store.user.model.Bike;
import com.store.user.model.Car;
import com.store.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll() {
        List<UserEntity> users = userService.getAll();
        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity user) {
        UserEntity newUser = userService.save(user);
        return ResponseEntity.ok(newUser);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") Long userId) {
        UserEntity user = userService.getUserById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Car> cars = userService.getCars(userId);
            return ResponseEntity.ok(cars);
        }
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackSaveCar")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") Long userId, @RequestBody Car car) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") Long userId) {
        UserEntity user = userService.getUserById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Bike> bikes = userService.getBikes(userId);
            return ResponseEntity.ok(bikes);
        }
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackSaveBike")
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") Long userId, @RequestBody Bike bike) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        Bike newBike = userService.saveBike(userId, bike);
        return ResponseEntity.ok(newBike);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallbackGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") Long userId) {
        Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallbackGetCars(@PathVariable("userId") Long userId, RuntimeException e) {
        return new ResponseEntity("User " + userId + "have the cars in the mechanic.", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallbackSaveCar(@PathVariable("userId") Long userId, @RequestBody Car car, RuntimeException e) {
        return new ResponseEntity("User " + userId + "do not have money to buy a car.", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallbackGetBikes(@PathVariable("userId") Long userId, RuntimeException e) {
        return new ResponseEntity("User " + userId + "have the bikes in the mechanic.", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallbackSaveBike(@PathVariable("userId") Long userId, @RequestBody Bike bike, RuntimeException e) {
        return new ResponseEntity("User " + userId + "do not have money to buy a bike.", HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> fallbackGetAll(@PathVariable("userId") Long userId, RuntimeException e) {
        return new ResponseEntity("User " + userId + "have the vehicles in the mechanic.", HttpStatus.OK);

    }
}
