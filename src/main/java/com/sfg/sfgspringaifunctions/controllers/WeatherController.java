package com.sfg.sfgspringaifunctions.controllers;

import com.sfg.sfgspringaifunctions.model.Answer;
import com.sfg.sfgspringaifunctions.model.Question;
import com.sfg.sfgspringaifunctions.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/weather-api")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping(path = "/ask", produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer ask(@RequestBody Question question) {
        return weatherService.getWeather(question);
    }
}
