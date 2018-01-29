FROM openjdk:8-jre
MAINTAINER David Flemström <charles.mccauley.5@gmail.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/iothub/iothub1.jar"]

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD target/lib           /usr/share/iothub/lib
# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/iothub/iothub.jar
