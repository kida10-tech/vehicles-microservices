package com.store.user.feignclient;

import com.store.user.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "car-service", path = "/api/car")
public interface CarFeignClient {

    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/byUser/{userId}")
    List<Car> getCars(@PathVariable("userId") Long userId);
}
