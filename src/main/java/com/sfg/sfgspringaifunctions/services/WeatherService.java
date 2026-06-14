package com.sfg.sfgspringaifunctions.services;

import com.sfg.sfgspringaifunctions.model.Answer;
import com.sfg.sfgspringaifunctions.model.Question;
import org.springframework.stereotype.Service;

public interface WeatherService {

    Answer getWeather(Question question);

}
