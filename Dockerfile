FROM kurron/docker-oracle-jdk-8

MAINTAINER Ron Kurr "kurr@jvmguy.com"

EXPOSE 8080

# switching to admin level user
USER root

ENTRYPOINT ["java", "-server", "-Xms128m", "-Xmx128m", "-Djava.awt.headless=true",  "-jar", "/opt/gelf.jar"]

COPY build/libs/gelf-generator-0.0.0.RELEASE-executable.jar /opt/gelf.jar
