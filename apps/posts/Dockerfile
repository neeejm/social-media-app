FROM gradle:jdk17-alpine AS GRADLE_BUILD

WORKDIR /app

COPY ./ ./

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar
RUN echo ${DB_HOST:-none}

FROM openjdk:17-alpine3.14

WORKDIR /app

COPY --from=GRADLE_BUILD /app/build/libs/posts-0.jar app.jar

RUN echo ${PORT:-8080}
EXPOSE ${PORT:-8080}
CMD [ "java", "-jar", "app.jar" ]