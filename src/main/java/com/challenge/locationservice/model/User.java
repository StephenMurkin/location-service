package com.challenge.locationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class User {

    @JsonProperty
    private long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty
    private String email;

    @JsonProperty("ip_address")
    private String ipAddress;

    @Getter
    @JsonProperty
    private double latitude;

    @Getter
    @JsonProperty
    private double longitude;
}
