# Reservation System

## Technologies

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Data JPA**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **MySQL (Database)**
- **Liquibase (Database Change Management)**

Swagger Docs URL:
```
http://localhost:8080/swagger-ui/index.html
```
## API List
### Reservation API:
```
curl --location --request POST 'http://localhost:8080/api/v1/reservations' \
--header 'Authorization: ••••••'
```

### Cancel Reservation API:
```
curl --location --request DELETE 'http://localhost:8080/api/v1/reservations/1'
```

### Authentication API:
```
curl --location 'http://localhost:8080/api/v1/users/authentication' \
--header 'Content-Type: application/json' \
--data '{
    "username": "user1",
    "password": "hashed_password_123"
}'
```

## Load Testing
### JMeter Test Result

![JMeter Result](https://github.com/sepideh-vaziry/reservation/blob/develop/files/jmeter-result.png?raw=true)