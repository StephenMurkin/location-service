This is a Spring Boot microservice which exposes user location information via a REST API. To run the service,   
run `./gradlew bootRun`. 

All users for a city can be retrieved by calling `GET localhost:8080/v1/users?city={city}`. Additionally, all users
within a range specified in miles can be retrieved by calling `GET localhost:8080/v1/users?city={city}&range={range}`.
This is currently computed using hardcoded city coordinates. An extension to this project could be to use a geolocation 
API (e.g. google maps) to dynamically look up city coordinates.

