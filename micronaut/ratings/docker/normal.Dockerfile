FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY build/libs/*-all.jar app.jar
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar app.jar