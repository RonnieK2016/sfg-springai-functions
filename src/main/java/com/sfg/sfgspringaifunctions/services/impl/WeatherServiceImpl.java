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
                .system("You are a weather service. You receive weather information from a service which gives you the information based on the metrics system." +
                        " When answering the weather in an imperial system country, you should convert the temperature to Fahrenheit and the wind speed to miles per hour. " +
                        "When reporting weather information obtained from tools, include:\n" +
                        "- temperature\n" +
                        "- feels like temperature\n" +
                        "- humidity\n" +
                        "- wind speed\n" +
                        "- wind direction\n" +
                        "- pressure\n" +
                        "- visibility\n" +
                        "- cloud cover\n" +
                        "- sunrise and sunset\n" +
                        "- precipitation probability\n" +
                        "if those values are available.")
                .tools(FunctionToolCallback.builder("CurrentWeather",
                                new WeatherServiceFunction(apiKey, weatherServiceUrl, geoService))
                        .description("Get the current weather for a location")
                        .inputType(WeatherRequest.class)
                        .build())
                .call()
                .content();

        return new Answer(response);
    }
}
