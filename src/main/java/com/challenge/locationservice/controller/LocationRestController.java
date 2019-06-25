package com.challenge.locationservice.controller;

import com.challenge.locationservice.model.User;
import com.challenge.locationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/users",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
public class LocationRestController {

    private final LocationService locationService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getUsersByCity(@RequestParam String city) {
        log.info("Received request to retrieve all users in {}", city);
        return ResponseEntity.ok(locationService.getUsersByCity(city));
    }
}
