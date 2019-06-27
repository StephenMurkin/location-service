package com.challenge.locationservice;

import com.challenge.locationservice.client.LocationRestClient;
import com.challenge.locationservice.model.User;
import com.challenge.locationservice.service.LocationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private static final long LONDON_USER_ID = 135;
    private static final long WATFORD_USER_ID = 136;
    private static final long BRISTOL_USER_ID = 137;

    @Before
    public void setup() {

        User londonUser = createTestUser(LONDON_USER_ID, 51.5074, 0.1278);
        User watfordUser = createTestUser(WATFORD_USER_ID, 51.6565, 0.3903);
        User bristolUser = createTestUser(BRISTOL_USER_ID, 51.4545, 2.5879);

        Set<User> allUsers = new HashSet<>();
        allUsers.add(londonUser);
        allUsers.add(watfordUser);
        allUsers.add(bristolUser);

        when(locationRestClient.getUsersByCity(LONDON)).thenReturn(Collections.singleton(londonUser));
        when(locationRestClient.getUsers()).thenReturn(allUsers);
    }

    @Test
    public void getUsersByCityNoRangeSpecified() throws Exception {

        String response = mockMvc.perform(
                get("/v1/users")
                        .param("city", LONDON)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<User> users = objectMapper.readValue(response, new TypeReference<List<User>>() {
        });
        assertEquals(1, users.size());
        assertEquals(LONDON_USER_ID, ReflectionTestUtils.getField(users.get(0), "id"));
    }

    @Test
    public void getUsersByCityRangeSpecified() throws Exception {

        String response = mockMvc.perform(
                get("/v1/users")
                        .param("city", LONDON)
                        .param("range", "50")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<User> users = objectMapper.readValue(response, new TypeReference<List<User>>(){});
        assertEquals(2, users.size());
    }

    @Test
    public void getUsersByCityUnsupportedCitySpecified() throws Exception {

        mockMvc.perform(
                get("/v1/users")
                        .param("city", "Paris")
                        .param("range", "50")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private static User createTestUser(long id, double latitude, double longitude) {

        User user = new User();
        ReflectionTestUtils.setField(user, "id", id);
        ReflectionTestUtils.setField(user, "firstName", "Mechelle");
        ReflectionTestUtils.setField(user, "lastName", "Boam");
        ReflectionTestUtils.setField(user, "email", "mboam3q@thetimes.co.uk");
        ReflectionTestUtils.setField(user, "ipAddress", "113.71.242.187");
        ReflectionTestUtils.setField(user, "latitude", latitude);
        ReflectionTestUtils.setField(user, "longitude", longitude);
        return user;
    }
}
