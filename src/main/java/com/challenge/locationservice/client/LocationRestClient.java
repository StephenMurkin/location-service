package com.challenge.locationservice.client;

import com.challenge.locationservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@FeignClient(name = "bptds", url = "${bptds.url}")
public interface LocationRestClient {

    @GetMapping(path = "/city/{city}/users", consumes = APPLICATION_JSON_VALUE)
    Set<User> getUsersByCity(@PathVariable String city);

    @GetMapping(path = "/users", consumes = APPLICATION_JSON_VALUE)
    Set<User> getUsers();
}
