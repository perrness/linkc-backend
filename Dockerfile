FROM azul/zulu-openjdk-alpine:20-latest
COPY target/linkc-backend-0.0.1-SNAPSHOT.jar linkc-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/linkc-backend-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080