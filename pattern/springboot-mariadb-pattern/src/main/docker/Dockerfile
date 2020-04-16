FROM openjdk:8-alpine

ARG JAR_FILE
ARG PROFILE

ENV TARGET_JAR_FILE ${JAR_FILE}
ENV JAVA_OPTS "-Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=${PROFILE}"

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en

RUN mkdir -p /logs
ADD ./target/${TARGET_JAR_FILE} /${TARGET_JAR_FILE}
VOLUME /tmp /logs
EXPOSE 8000
ENTRYPOINT ["sh", "-c", "java $JARA_OPTS -jar /${TARGET_JAR_FILE}"]
