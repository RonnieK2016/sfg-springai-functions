package com.sfg.sfgspringaifunctions.services.impl;

import org.springframework.ai.chat.model.ChatModel;
import com.sfg.sfgspringaifunctions.model.Answer;
import com.sfg.sfgspringaifunctions.model.Question;
import com.sfg.sfgspringaifunctions.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {

    private final ChatModel chatModel;

    @Override
    public Answer getWeather(Question question) {
        return new Answer("to-do");
    }
}
