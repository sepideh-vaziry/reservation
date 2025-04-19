# Reservation System

Swagger Docs URL:
```
http://localhost:8080/swagger-ui/index.html
```

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