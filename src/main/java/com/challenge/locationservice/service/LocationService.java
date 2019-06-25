package com.challenge.locationservice.service;

import com.challenge.locationservice.client.LocationRestClient;
import com.challenge.locationservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRestClient locationRestClient;

    public List<User> getUsersByCity(String city) {
        return locationRestClient.getUsersByCity(city);
    }
}
