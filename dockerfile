FROM openjdk:11
ADD target/atm-1.0.jar atm.jar
ENTRYPOINT ["java", "-jar", "atm.jar"]