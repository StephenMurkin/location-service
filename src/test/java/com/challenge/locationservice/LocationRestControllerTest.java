package com.challenge.locationservice;

import com.challenge.locationservice.client.LocationRestClient;
import com.challenge.locationservice.model.User;
import com.challenge.locationservice.service.LocationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class LocationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationRestClient locationRestClient;

    private static final String LONDON = "London";
    private static final long ID = 135;
    private static final String FIRST_NAME = "Mechelle";

    @Test
    public void getUsersByCity() throws Exception {

        User user = new User();
        ReflectionTestUtils.setField(user, "id", ID);
        ReflectionTestUtils.setField(user, "firstName", FIRST_NAME);
        ReflectionTestUtils.setField(user, "lastName", "Boam");
        ReflectionTestUtils.setField(user, "email", "mboam3q@thetimes.co.uk");
        ReflectionTestUtils.setField(user, "ipAddress", "113.71.242.187");
        ReflectionTestUtils.setField(user, "latitude", -6.5115909);
        ReflectionTestUtils.setField(user, "longitude", 105.652983);

        when(locationRestClient.getUsersByCity(LONDON))
                .thenReturn(Collections.singletonList(user));

        String response = mockMvc.perform(
                get("/v1/users")
                        .param("city", LONDON)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<User> users = objectMapper.readValue(response, new TypeReference<List<User>>(){});
        assertEquals(1, users.size());
        assertEquals(ID, ReflectionTestUtils.getField(users.get(0), "id"));
        assertEquals(FIRST_NAME, ReflectionTestUtils.getField(users.get(0), "firstName"));
    }
}
