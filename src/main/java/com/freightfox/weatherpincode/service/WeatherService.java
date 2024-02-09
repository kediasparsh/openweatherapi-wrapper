package com.freightfox.weatherpincode.service;

import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.freightfox.weatherpincode.entity.Location;
import com.freightfox.weatherpincode.entity.Weather;
import com.freightfox.weatherpincode.repository.LocationRepository;
import com.freightfox.weatherpincode.repository.WeatherRepository;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private LocationRepository locationRepository;

    private RestTemplate restTemplate = new RestTemplate();
    private final String openWeatherMapAPIKey = "aae4fbd520364da73408fb7e5ba65ad4";

    public Weather saveWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    public Weather getWeather(int pincode, LocalDate date) {
        Weather weatherInDB = weatherRepository.findByPincodeAndDate(pincode, date);
        if (weatherInDB != null) {
            return weatherInDB;
        }

        Location location = locationRepository.findById(pincode).orElse(null);
        if (location == null) {
            location = fetchAndSaveLocation(pincode);
        }

        Weather weather = fetchWeather(location.getLatitude(), location.getLongitude(), pincode, date);
        if (weather != null) saveWeather(weather);
        return weather;
    }

    public Location fetchAndSaveLocation(int pincode) {
        String geocodingAPIURL = "http://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",IN&appid=" + openWeatherMapAPIKey;
        ResponseEntity<String> response = restTemplate.getForEntity(geocodingAPIURL, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseJson = new JSONObject(response.getBody());
			Double latitude = responseJson.getDouble("lat");
			Double longitude = responseJson.getDouble("lon");

			Location location = new Location();
			location.setPincode(pincode);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			locationRepository.save(location);
			return location;
        }
        return null;
    }

    public Weather fetchWeather(Double latitude, Double longitude, int pincode, LocalDate date) {
        String weatherAPIURL = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + openWeatherMapAPIKey;
        ResponseEntity<String> response = restTemplate.getForEntity(weatherAPIURL, String.class);
        String responseBody = response.getBody();

        if (response.getStatusCode() == HttpStatus.OK && responseBody != null) {
			JSONObject responseJson = new JSONObject(responseBody);

            JSONArray weatherArray = responseJson.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String description = weatherObject.getString("description");

            JSONObject mainObject = responseJson.getJSONObject("main");
            Double temperature = mainObject.getDouble("temp");
            Integer pressure = mainObject.getInt("pressure");
            Integer humidity = mainObject.getInt("humidity");


            Weather weather = new Weather();
            weather.setPincode(pincode);
            weather.setDate(date);
            weather.setTemperature(temperature);
            weather.setPressure(pressure);
            weather.setHumidity(humidity);
            weather.setDescription(description);
            return weather;
		}
        return null;
    }
}
