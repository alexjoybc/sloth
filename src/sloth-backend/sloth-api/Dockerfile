FROM maven:3.6.3-jdk-11 as build

WORKDIR /

COPY . .

RUN ls

RUN mvn package -Dquarkus.package.type=fast-jar

RUN ls

####
# This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode
#
# Before building the container image run:
#
# ./mvnw package -Dquarkus.package.type=fast-jar
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile.fast-jar -t quarkus/sloth-api-fast-jar .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/sloth-api-fast-jar
#
# If you want to include the debug port into your docker image
# you will have to expose the debug port (default 5005) like this :  EXPOSE 8080 5050
#
# Then run the container using :
#
# docker run -i --rm -p 8080:8080 -p 5005:5005 -e JAVA_ENABLE_DEBUG="true" quarkus/sloth-api-fast-jar
#
###
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.3 

COPY --from=build ./target /target

RUN ls /target/quarkus-app

ARG JAVA_PACKAGE=java-11-openjdk-headless
ARG RUN_JAVA_VERSION=1.3.8
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'
# Install java and the run-java script
# Also set up permissions for user `1001`
RUN microdnf install curl ca-certificates ${JAVA_PACKAGE} \
    && microdnf update \
    && microdnf clean all \
    && mkdir /deployments \
    && chown 1001 /deployments \
    && chmod "g+rwX" /deployments \
    && chown 1001:root /deployments \
    && curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o /deployments/run-java.sh \
    && chown 1001 /deployments/run-java.sh \
    && chmod 540 /deployments/run-java.sh \
    && echo "securerandom.source=file:/dev/urandom" >> /etc/alternatives/jre/lib/security/java.security

# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
# We make four distinct layers so if there are application changes the library layers can be re-used

RUN ls target/quarkus-app/lib

RUN cp -R /target/quarkus-app/lib/ /deployments/lib/
RUN cp -R /target/quarkus-app/*.jar /deployments/
RUN cp -R /target/quarkus-app/app/ /deployments/app/
RUN cp -R /target/quarkus-app/quarkus/ /deployments/quarkus/

RUN chown 1001:1001 /deployments/lib/
RUN chown 1001:1001 /deployments/
RUN chown 1001:1001 /deployments/app/
RUN chown 1001:1001 /deployments/quarkus/

EXPOSE 8080
USER 1001

ENTRYPOINT [ "/deployments/run-java.sh" ]
