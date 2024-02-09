package com.freightfox.weatherpincode.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.freightfox.weatherpincode.entity.Weather;
import com.freightfox.weatherpincode.repository.LocationRepository;
import com.freightfox.weatherpincode.repository.WeatherRepository;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {
    
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void getWeatherTest() {
        Weather weather = new Weather();
        weather.setPincode(395007);
        weather.setDate(LocalDate.of(2024,2,5));
        weather.setTemperature(304.1);
        weather.setPressure(1014);
        weather.setHumidity(29);
        weather.setDescription("clear sky");

        when(weatherRepository.findByPincodeAndDate(weather.getPincode(), weather.getDate())).thenReturn(weather);
        Weather result = weatherService.getWeather(weather.getPincode(), weather.getDate());

        assertNotNull(result);
        assertEquals(395007, result.getPincode());
        assertEquals(LocalDate.of(2024,2,5), result.getDate());
        assertEquals(304.1, result.getTemperature(), 0);
        assertEquals(1014, result.getPressure());
        assertEquals(29, result.getHumidity());
        assertEquals("clear sky", result.getDescription());
    }

}
