FROM maven:3.6.0-jdk-11-slim
COPY . /app
WORKDIR /app
RUN mvn clean install
ENTRYPOINT ["java","-jar","/app/target/infosw-0.0.1-SNAPSHOT.jar"]