package com.challenge.locationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CityNotSupportedException extends RuntimeException {

    public CityNotSupportedException(String city) {
        super("City " + city + " not supported currently");
    }
}
