FROM openjdk:8-jre-alpine

EXPOSE 8080

ADD target/demo-0.0.1-SNAPSHOT.jar app.jar

ADD start.sh start.sh

RUN sh -c 'chmod u+x /start.sh'
ENTRYPOINT ["./start.sh"]