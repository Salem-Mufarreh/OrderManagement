FROM openjdk:17-jdk
LABEL authors="Sale Mufarreh"
WORKDIR /app
COPY build/libs/OrderManagement-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "OrderManagement-0.0.1-SNAPSHOT.jar"]

