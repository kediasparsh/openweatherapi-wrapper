package com.freightfox.weatherpincode.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.freightfox.weatherpincode.entity.Weather;
import com.freightfox.weatherpincode.service.WeatherService;

@RestController
public class WeatherPincodeController {

    @Autowired
    private WeatherService weatherService;
    
    @GetMapping("/weather")
    public ResponseEntity<Weather> getWeather(@RequestParam("pincode") int pincode, @RequestParam("for_date") LocalDate date) {
        try {
            Weather weather = weatherService.getWeather(pincode, date);
            return new ResponseEntity<>(weather, HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
