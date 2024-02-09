package com.freightfox.weatherpincode.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import com.freightfox.weatherpincode.entity.Weather;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {
    Weather findByPincodeAndDate(int pincode, LocalDate date);
}
