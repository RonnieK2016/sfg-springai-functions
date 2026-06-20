package com.sfg.sfgspringaifunctions.services.impl;


import com.sfg.sfgspringaifunctions.functions.WeatherServiceFunction;
import com.sfg.sfgspringaifunctions.model.Answer;
import com.sfg.sfgspringaifunctions.model.Question;
import com.sfg.sfgspringaifunctions.model.WeatherRequest;
import com.sfg.sfgspringaifunctions.services.GeoService;
import com.sfg.sfgspringaifunctions.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${sfg.api.api-ninjas-key}")
    String apiKey;

    @Value("${sfg.api.weather-service-url}")
    String weatherServiceUrl;

    private final OpenAiChatModel chatModel;

    private final GeoService geoService;

    @Override
    public Answer getWeather(Question question) {
        String response = ChatClient.create(chatModel).prompt()
                .user(question.question())
                .tools(FunctionToolCallback.builder("CurrentWeather", new WeatherServiceFunction(apiKey, weatherServiceUrl, geoService))
                        .description("Get the current weather for a location")
                        .inputType(WeatherRequest.class)
                        .build())
                .call()
                .content();

        return new Answer(response);
    }
}
