FROM openjdk:11
ADD ./target/user-service-0.0.1-SNAPSHOT.jar /usr/src/user-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "user-service-0.0.1-SNAPSHOT.jar"]

FROM jenkins:latest
MAINTAINER Alan Gomes Coimbra <algcoimbra01@gmail.com>

VOLUME /var/jenkins_home

EXPOSE 50000
EXPOSE 8080