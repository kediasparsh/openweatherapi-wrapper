package com.freightfox.weatherpincode.service;

import org.springframework.stereotype.Service;

import com.freightfox.weatherpincode.entity.Location;
import com.freightfox.weatherpincode.repository.LocationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LocationService {

    private LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location getLocation(int pincode) {
        return locationRepository.findById(pincode).orElse(null);
    }    
}
