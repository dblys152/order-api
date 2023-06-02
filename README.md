# order-api

### 개발환경
- Spring Boot 3.1.0
- Java 17
- H2
- Spring Data Jpa & QueryDsl

### API
swagger-ui : http://localhost:8080/swagger-ui/index.html

[선주문]
- POST http://localhost:8080/v1/orders
  - -H ContentType: application/json

[주문 완료]
- PATCH http://localhost:8080/v1/orders/{orderId}/complete
    - -H ContentType: application/json

[주문 단건 조회]
- GET http://localhost:8080/v1/orders/{orderId}

[유저 주문 내역 조회]
- GET http://localhost:8080/v1/orders
  - query param: userId


