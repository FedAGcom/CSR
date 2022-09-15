FROM openjdk:11

ADD target/csr.jar csr.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "csr.jar"]

