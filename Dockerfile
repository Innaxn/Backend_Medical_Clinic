FROM gradle:7.5.1-jdk17
WORKDIR /opt/app
COPY ./ ./
RUN ./gradlew assemble

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar build/libs/SanoMed-0.0.1-SNAPSHOT.jar"]