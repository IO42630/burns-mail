FROM eclipse-temurin:17-alpine
COPY target/burns-mail-0.1.jar burns-mail-0.1.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","/burns-mail-0.1.jar"]