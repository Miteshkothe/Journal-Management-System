package com.restApi.journalApp.Service;

import com.restApi.journalApp.Entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class weatherApi {
    private static final String apiKey="8e66e1d586a2a70747bbe995c9c76899";
    private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisService redisService;
    public Weather getWeather(String city){
        Weather weather = redisService.get("weather_of_" + city, Weather.class);
        if(weather!=null){
            return weather;
        }else{
            String finalAPI=API.replace("CITY",city).replace("API_KEY",apiKey);
            ResponseEntity<Weather> exchange = restTemplate.exchange(finalAPI, HttpMethod.GET, null, Weather.class);
            Weather body = exchange.getBody();
            if(body!=null){
                redisService.set("weather_of_"+city,body,300l);
            }return body;
        }


    }
}
