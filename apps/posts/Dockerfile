FROM openjdk:17-alpine3.14

WORKDIR /app

COPY ./build/libs/posts-0.jar app.jar

EXPOSE ${PORT:-8080}
CMD [ "java", "-jar", "app.jar" ]