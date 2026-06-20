package com.sfg.sfgspringaifunctions.functions;

import com.sfg.sfgspringaifunctions.model.GeoRecord;
import com.sfg.sfgspringaifunctions.model.WeatherRequest;
import com.sfg.sfgspringaifunctions.model.WeatherResponse;
import com.sfg.sfgspringaifunctions.services.GeoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@AllArgsConstructor
@Slf4j
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    private String apiNinjasApiKey;

    private String weatherServiceUrl;

    private GeoService geoService;

    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(weatherServiceUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiNinjasApiKey);
                    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();

        GeoRecord geoRecord = geoService.getGeoRecord(weatherRequest);

        if (geoRecord == null) {
            log.info("Can't find geoRecord for location: {} and state: {} and countryCode: {}", weatherRequest.location(), weatherRequest.state(), weatherRequest.countryCode());
            return null;
        }

        return restClient.get().uri(uriBuilder -> {
            log.info("Building request for weather request - {}", geoRecord);
            uriBuilder.queryParam("lat", geoRecord.latitude());
            uriBuilder.queryParam("lon", geoRecord.longitude());
            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);
    }
}
