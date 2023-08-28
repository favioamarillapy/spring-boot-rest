# spring-boot-rest
Api rest proyect development with Spring boot and Java 17

## Features
- Spring security
- Spring data
- Pageable

## Run
- Build proyect `mvnw clean install`
- Run proyect `mvnw spring-boot:run`
- Proyect running in [http://localhost:8080/api](http://localhost:8080/api)

## Documentation
You can the API documentation in: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Sonarqube
You can run the following command to examine the code
`mvn clean verify sonar:sonar -Dsonar.projectKey=spring-boot-rest -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_7c80a793ec0360431788f5fc972fd72addedfb24`
