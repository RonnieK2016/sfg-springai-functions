package com.sfg.sfgspringaifunctions.services.impl;

import com.sfg.sfgspringaifunctions.model.GeoRecord;
import com.sfg.sfgspringaifunctions.model.GeocodingResponse;
import com.sfg.sfgspringaifunctions.model.WeatherRequest;
import com.sfg.sfgspringaifunctions.model.WeatherResponse;
import com.sfg.sfgspringaifunctions.services.GeoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class OpenMeteoGeoService implements GeoService {

    @Value("${sfg.api.geo-service-url}")
    private String serviceUrl;

    @Override
    public GeoRecord getGeoRecord(WeatherRequest weatherRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(serviceUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();

        GeocodingResponse response = restClient.get().uri(uriBuilder -> {
            log.info("Building request for weather request - {}", weatherRequest);

            uriBuilder.path("/search");
            uriBuilder.queryParam("name", weatherRequest.location());
            uriBuilder.queryParam("countryCode", weatherRequest.countryCode());
            uriBuilder.queryParam("count", 10);
            uriBuilder.queryParam("format", "json");

            return uriBuilder.build();
        }).retrieve().body(GeocodingResponse.class);


        if(response != null && !CollectionUtils.isEmpty(response.results())) {
            if(response.results().size() == 1 || ObjectUtils.isEmpty(weatherRequest.state())) {
                GeocodingResponse.Location location = response.results().getFirst();
                return new GeoRecord(location.latitude(), location.longitude());
            }

            for(GeocodingResponse.Location location : response.results()) {
                if(weatherRequest.state().equals(location.admin1())) {
                    return new GeoRecord(location.latitude(), location.longitude());
                }
            }

            log.info("Can't find data matchin record for request {}", weatherRequest);

            return null;
        }

        log.info("Can't find data in openGeoService for request {}", weatherRequest);
        return null;
    }
}
