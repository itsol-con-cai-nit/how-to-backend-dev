package com.o8kgroup.swagger.controllers;

import com.o8kgroup.swagger.model.Car;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class Cars {

    List<Car> cars = new ArrayList<>();

    @GetMapping
    public List<Car> listAll() {
        return cars;
    }

    @PostMapping
    public void save(String name) {
        Car car = new Car(name);
        cars.add(car);
    }
}
