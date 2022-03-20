package com.store.user.service;

import com.store.user.entity.UserEntity;
import com.store.user.feignclient.BikeFeignClient;
import com.store.user.feignclient.CarFeignClient;
import com.store.user.model.Bike;
import com.store.user.model.Car;
import com.store.user.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRespository userRespository;

    private final RestTemplate restTemplate;

    private final CarFeignClient carFeignClient;

    private final BikeFeignClient bikeFeignClient;

    public List<UserEntity> getAll() {
        return userRespository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRespository.findById(id).orElse(null);
    }

    public UserEntity save(UserEntity user) {
        return userRespository.save(user);
    }

    public List<Car> getCars(Long userId) {
        return restTemplate.getForObject("http://car-service/api/car/byUser/" + userId, List.class);
    }

    public List<Bike> getBikes(Long userId) {
        return restTemplate.getForObject("http://bike-service/api/bike/byUser/" + userId, List.class);
    }

    public Car saveCar(Long userId, Car car) {
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Bike saveBike(Long userId, Bike bike) {
        bike.setUserId(userId);
        return bikeFeignClient.save(bike);
    }

    public Map<String, Object> getUserAndVehicles(Long userId) {
        Map<String, Object> resultSet = new HashMap<>();

        UserEntity user = userRespository.findById(userId).orElse(null);

        if(user == null) {
            resultSet.put("Message", "User not exist.");
            return resultSet;
        }
        resultSet.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if(cars.isEmpty()) {
            resultSet.put("Cars","this user does not have cars.");
        } else {
            resultSet.put("Cars", cars);
        }
        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if(bikes.isEmpty()) {
            resultSet.put("Bikes", "this user does not have bikes.");
        } else {
            resultSet.put("Bikes", bikes);
        }
        return resultSet;
    }
}
