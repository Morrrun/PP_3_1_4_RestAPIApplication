FROM openjdk:17
ADD /target/spring-boot_security-demo-0.0.1-SNAPSHOT.jar my-first-app.jar
ENTRYPOINT ["java", "-jar", "my-first-app.jar"]