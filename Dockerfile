FROM java:8

EXPOSE 7070

ADD target/mts.jar mts.jar

ENTRYPOINT ["java","-jar","mts.jar"]