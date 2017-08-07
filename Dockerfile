FROM openjdk:8

ADD target/*.jar /the-courtroom/
ADD src/main/resources/*.properties /the-courtroom/

EXPOSE 8080

ENTRYPOINT java -jar /the-courtroom/*.jar