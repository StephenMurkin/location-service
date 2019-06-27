package com.challenge.locationservice.service;

import com.challenge.locationservice.client.LocationRestClient;
import com.challenge.locationservice.exception.CityNotSupportedException;
import com.challenge.locationservice.model.User;
import lombok.RequiredArgsConstructor;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRestClient locationRestClient;
    private static final String LONDON = "London";
    private static final double LONDON_LATITUDE = 51.5074;
    private static final double LONDON_LONGITUDE = 0.1278;

    public Set<User> getUsersByCity(String city) {
        return locationRestClient.getUsersByCity(city);
    }

    public Set<User> getUsersByCity(String city, double range) {

        Set<User> allUsers = locationRestClient.getUsers();
        Set<User> inRangeUsers = new HashSet<>();
        GeodeticCalculator geoCalculator = new GeodeticCalculator();

        if (!city.equals(LONDON)) {
            throw new CityNotSupportedException(city);
        }

        GlobalCoordinates cityCoordinates = new GlobalCoordinates(LONDON_LATITUDE, LONDON_LONGITUDE);

        for (User user : allUsers) {

            GlobalCoordinates userCoordinates = new GlobalCoordinates(user.getLatitude(), user.getLongitude());
            GeodeticCurve geoCurve = geoCalculator.calculateGeodeticCurve(Ellipsoid.WGS84, cityCoordinates, userCoordinates);
            double distanceMiles = geoCurve.getEllipsoidalDistance() / 1000.0 * 0.621371192;

            if (distanceMiles < range) {
                inRangeUsers.add(user);
            }
        }

        Set<User> cityUsers = getUsersByCity(city);
        inRangeUsers.addAll(cityUsers);
        return new HashSet<>(inRangeUsers);
    }
}
