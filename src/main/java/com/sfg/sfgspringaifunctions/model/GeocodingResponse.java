package com.sfg.sfgspringaifunctions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

public record GeocodingResponse(

        @JsonProperty("results")
        @JsonPropertyDescription("List of matching locations")
        List<Location> results
) {

    public record Location(

            @JsonProperty("id")
            @JsonPropertyDescription("Unique ID for this location")
            Integer id,

            @JsonProperty("name")
            @JsonPropertyDescription("Localized location name")
            String name,

            @JsonProperty("latitude")
            @JsonPropertyDescription("Latitude in WGS84 coordinates")
            Double latitude,

            @JsonProperty("longitude")
            @JsonPropertyDescription("Longitude in WGS84 coordinates")
            Double longitude,

            @JsonProperty("elevation")
            @JsonPropertyDescription("Elevation above mean sea level in meters")
            Double elevation,

            @JsonProperty("feature_code")
            @JsonPropertyDescription("GeoNames feature code identifying location type")
            String featureCode,

            @JsonProperty("country_code")
            @JsonPropertyDescription("ISO-3166-1 alpha-2 country code")
            String countryCode,

            @JsonProperty("country")
            @JsonPropertyDescription("Localized country name")
            String country,

            @JsonProperty("country_id")
            @JsonPropertyDescription("Unique ID of the country")
            Integer countryId,

            @JsonProperty("timezone")
            @JsonPropertyDescription("IANA timezone identifier")
            String timezone,

            @JsonProperty("population")
            @JsonPropertyDescription("Population count")
            Long population,

            @JsonProperty("postcodes")
            @JsonPropertyDescription("Associated postal codes")
            List<String> postcodes,

            @JsonProperty("admin1")
            @JsonPropertyDescription("First administrative level name")
            String admin1,

            @JsonProperty("admin2")
            @JsonPropertyDescription("Second administrative level name")
            String admin2,

            @JsonProperty("admin3")
            @JsonPropertyDescription("Third administrative level name")
            String admin3,

            @JsonProperty("admin4")
            @JsonPropertyDescription("Fourth administrative level name")
            String admin4,

            @JsonProperty("admin1_id")
            @JsonPropertyDescription("Unique ID of first administrative level")
            Integer admin1Id,

            @JsonProperty("admin2_id")
            @JsonPropertyDescription("Unique ID of second administrative level")
            Integer admin2Id,

            @JsonProperty("admin3_id")
            @JsonPropertyDescription("Unique ID of third administrative level")
            Integer admin3Id,

            @JsonProperty("admin4_id")
            @JsonPropertyDescription("Unique ID of fourth administrative level")
            Integer admin4Id
    ) {
    }
}