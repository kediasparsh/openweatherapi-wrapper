package com.freightfox.weatherpincode.repository;

import org.springframework.data.repository.CrudRepository;

import com.freightfox.weatherpincode.entity.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    
}
