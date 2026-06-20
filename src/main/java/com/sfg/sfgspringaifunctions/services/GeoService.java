package com.sfg.sfgspringaifunctions.services;

import com.sfg.sfgspringaifunctions.model.GeoRecord;
import com.sfg.sfgspringaifunctions.model.WeatherRequest;

public interface GeoService {

    GeoRecord getGeoRecord(WeatherRequest request);
}
