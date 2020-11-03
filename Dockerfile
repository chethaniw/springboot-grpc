#instruction initializes a new build stage and sets the Base Image for subsequent instructions.
#alpine - to reduce image size
FROM openjdk:8-jdk-alpine

#instruction creates a mount point with the specified name
#and marks it as holding externally mounted volumes from native host or other containers
VOLUME /tmp

#instruction informs Docker that the container listens on the specified network ports at runtime
EXPOSE 8082

#instruction will execute any commands in a new layer on top of the current image and commit the results
RUN mkdir -p /app/
RUN mkdir -p /app/logs/

#instruction copies new files, directories or remote file URLs from <src>
#and adds them to the filesystem of the image at the path <dest>
ADD build/libs/springboot-grpc-0.0.1-SNAPSHOT.jar /app/app.jar

#allows you to configure a container that will run as an executable
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]